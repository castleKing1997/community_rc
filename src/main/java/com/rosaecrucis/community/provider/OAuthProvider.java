package com.rosaecrucis.community.provider;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rosaecrucis.community.dto.AccessTokenDTO;
import com.rosaecrucis.community.dto.GithubUserDTO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class OAuthProvider {
	// 返回获取用户信息需要的token
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		// 请求token的接口
		String url = "https://github.com/login/oauth/access_token";
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		// 创建post请求
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(100000, TimeUnit.MILLISECONDS)
				.readTimeout(100000, TimeUnit.MILLISECONDS).build();
		// 将对象解析为JSON作为请求体
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			// 请求得到response对象，读取返回的token
			String string = response.body().string();
			String token = string.split("&")[0].split("=")[1];
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 返回用户信息
	public GithubUserDTO getGithutUser(String accessToken) {
		if (accessToken == null) {
			return null;
		}
		// 请求用户信息的接口
		String url = "https://api.github.com/user";
		// 创建get请求
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(100000, TimeUnit.MILLISECONDS)
				.readTimeout(100000, TimeUnit.MILLISECONDS).build();
		Request request = new Request.Builder().url(url).header("Authorization", "token " + accessToken).build();
		try (Response response = client.newCall(request).execute()) {
			// 返回包含用户信息的reponse
			String string = response.body().string();
			// 将JSON解析为用户信息对象
			GithubUserDTO githubUser = JSON.parseObject(string, GithubUserDTO.class);
			return githubUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
