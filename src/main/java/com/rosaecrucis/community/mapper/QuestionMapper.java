package com.rosaecrucis.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.rosaecrucis.community.model.Question;

@Mapper
public interface QuestionMapper {

	// 插入问题
	@Insert("INSERT INTO QUESTION (title, description, tag,creator, gmt_create, gmt_modified) values (#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
	public void insertQuestion(Question question);

	// 查询全部问题
	@Select("SELECT * FROM QUESTION")
	public List<Question> getQuestions();
}
