package com.rosaecrucis.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.rosaecrucis.community.model.User;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO USER (name, account_id, token, gmt_create, gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
	public void insertUser(User user);

	@Select("SELECT * FROM user WHERE token=#{token}")
	public User findByToken(@Param("token") String token);
}
