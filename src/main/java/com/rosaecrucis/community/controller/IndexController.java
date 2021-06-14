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
	// 在IOC容器中查找类别，自动注入对象
	@Autowired
	private UserMapper userMapper;

	// 对主页面发出Get请求时操作
	@GetMapping("/")
	// 通过HttpServletRequest对象可以获取servlet服务，如访问cookie和session
	public String index(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			// 获取属性名为token的cookie
			if (cookie.getName().equals("token")) {
				String token = cookie.getValue();
				// 使用数据模型映射，获取token值对应的user对象
				User user = userMapper.findByToken(token);
				if (user != null) {
					// 向session中添加user对象
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		}
		// 返回index。html（此时也应该返回了session）
		return "index";
	}
}
