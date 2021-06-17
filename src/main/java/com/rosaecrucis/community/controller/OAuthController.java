package com.rosaecrucis.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.rosaecrucis.community.dto.AccessTokenDTO;
import com.rosaecrucis.community.dto.GithubUserDTO;
import com.rosaecrucis.community.mapper.UserMapper;
import com.rosaecrucis.community.model.User;
import com.rosaecrucis.community.provider.OAuthProvider;

@Controller
public class OAuthController {

	@Autowired
	private OAuthProvider provider;
	// 获取配置文件中的值
	@Value("${github.redirect.uri}")
	private String redirectURI;
	@Value("${github.client.id}")
	private String clientID;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Autowired
	private UserMapper userMapper;

	// 向/loginOAuth发Get请求时操作
	@GetMapping("/loginOAuth")
	// @RequestParam可以获取url中?之后的参数
	public String loginOAuth(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
			HttpServletResponse response) {
		// 使用认证得到的code，请求获取用户信息需要的token
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectURI);
		accessTokenDTO.setClient_id(clientID);
		accessTokenDTO.setClient_secret(clientSecret);
		System.out.println("Logging...");
		String token = provider.getAccessToken(accessTokenDTO);
		System.out.println("Get Token:" + token);
		// 使用获得的token请求用户信息
		GithubUserDTO githubUser = provider.getGithutUser(token);
		System.out.println("Get User:" + JSON.toJSONString(githubUser));
		if (githubUser != null) {
			// 登录成功
			// 使用获取的用户信息，创建user对象，写入数据库
			User user = new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			user.setAvatarUrl(githubUser.getAvatarUrl());
			user.setBio(githubUser.getBio());
			userMapper.insertUser(user);
			// 将token放到response的cookie中，返回给用户，用户收到后会把token保存在浏览器中
			token = user.getToken();
			response.addCookie(new Cookie("token", token));
			// request.getSession().setAttribute("user", githubUser);
			// 重定向到主页面，会产生页面跳转
			return "redirect:/";
		} else {
			// 登录失败
			return "redirect:/";
		}
	}
}
