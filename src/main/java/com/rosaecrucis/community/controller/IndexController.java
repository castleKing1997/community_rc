package com.rosaecrucis.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rosaecrucis.community.mapper.UserMapper;
import com.rosaecrucis.community.model.User;

@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				String token = cookie.getValue();
				User user = userMapper.findByToken(token);
				if (user != null) {
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		}
		return "index";
	}
}
