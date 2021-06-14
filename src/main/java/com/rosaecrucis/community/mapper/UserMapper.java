package com.rosaecrucis.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.rosaecrucis.community.model.User;

// 数据模型映射，将数据模型和数据库表中的数据绑定
@Mapper
public interface UserMapper {

	// 插入新的用户信息
	@Insert("INSERT INTO USER (name, account_id, token, gmt_create, gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
	public void insertUser(User user);

	// 根据token查询用户信息
	@Select("SELECT * FROM user WHERE token=#{token}")
	public User findByToken(@Param("token") String token);
}
