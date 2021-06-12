package com.rosaecrucis.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.rosaecrucis.community.dto.AccessTokenDTO;
import com.rosaecrucis.community.dto.GithubUserDTO;
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

	@GetMapping("/loginOAuth")
	public String loginOAuth(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
			HttpServletRequest request) {
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
			request.getSession().setAttribute("user", githubUser);
			return "redirect:/";
		} else {
			// 登录失败
			return "redirect:/";
		}
	}
}