package com.rosaecrucis.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosaecrucis.community.dto.QuestionDTO;
import com.rosaecrucis.community.mapper.QuestionMapper;
import com.rosaecrucis.community.mapper.UserMapper;
import com.rosaecrucis.community.model.Question;
import com.rosaecrucis.community.model.User;

@Service
public class QuestionService {

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> getQuestions() {
		// 获取所有问题
		List<Question> questions = questionMapper.getQuestions();
		// 保存问题DTO
		List<QuestionDTO> questionList = new ArrayList<>();
		for (Question question : questions) {
			QuestionDTO questionDTO = new QuestionDTO();
			// 获取创建问题的用户信息
			User user = userMapper.getById(question.getCreator());
			// 将信息集合再DTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionList.add(questionDTO);
		}
		return questionList;
	}
}
