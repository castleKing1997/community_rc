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
	@Value("${github.redirect.uri}")
	private String redirectURI;
	@Value("${github.client.id}")
	private String clientID;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/loginOAuth")
	public String loginOAuth(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
			HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectURI);
		accessTokenDTO.setClient_id(clientID);
		accessTokenDTO.setClient_secret(clientSecret);
		System.out.println("Logging...");
		String token = provider.getAccessToken(accessTokenDTO);
		System.out.println("Get Token:" + token);
		GithubUserDTO githubUser = provider.getGithutUser(token);
		System.out.println("Get User:" + JSON.toJSONString(githubUser));
		if (githubUser != null) {
			// 登录成功
			User user = new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insertUser(user);
			token = user.getToken();
			response.addCookie(new Cookie("token", token));
			// request.getSession().setAttribute("user", githubUser);
			return "redirect:/";
		} else {
			// 登录失败
			return "redirect:/";
		}
	}
}
