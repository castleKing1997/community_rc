package com.rosaecrucis.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rosaecrucis.community.mapper.QuestionMapper;
import com.rosaecrucis.community.model.Question;
import com.rosaecrucis.community.model.User;

@Controller
public class PublishController {

	@Autowired
	private QuestionMapper questionMapper;

	// 访问提问页面
	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}

	// 提交问题信息
	@PostMapping("/publish")
	public String doPublish(@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("tag") String tag, HttpServletRequest request, Model model) {
		// 将变量保存到当前浏览器页面中，如果发生跳转会清空
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		// 判断是否填写合法
		if (title == "" || title == null) {
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if (description == "" || description == null) {
			model.addAttribute("error", "内容不能为空");
			return "publish";
		}
		if (tag == "" || tag == null) {
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}
		// 从session中获取User对象。并转为User对象
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		// 将问题插入数据库
		Question question = new Question();
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());

		questionMapper.insertQuestion(question);
		return "redirect:/";
	}
}
