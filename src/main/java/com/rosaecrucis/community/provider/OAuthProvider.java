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
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		String url = "https://github.com/login/oauth/access_token";
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(100000, TimeUnit.MILLISECONDS)
				.readTimeout(100000, TimeUnit.MILLISECONDS).build();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			String token = string.split("&")[0].split("=")[1];
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public GithubUserDTO getGithutUser(String accessToken) {
		if (accessToken == null) {
			return null;
		}
		String url = "https://api.github.com/user";
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(100000, TimeUnit.MILLISECONDS)
				.readTimeout(100000, TimeUnit.MILLISECONDS).build();
		Request request = new Request.Builder().url(url).header("Authorization", "token " + accessToken).build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			GithubUserDTO githubUser = JSON.parseObject(string, GithubUserDTO.class);
			return githubUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
