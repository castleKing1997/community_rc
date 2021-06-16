package com.rosaecrucis.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.rosaecrucis.community.model.Question;

@Mapper
public interface QuestionMapper {

	// 插入问题
	@Insert("INSERT INTO QUESTION (title, description, tag,creator, gmt_create, gmt_modified) values (#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
	public void insertQuestion(Question question);
}
