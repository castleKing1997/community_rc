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
	@Insert("INSERT INTO USER (name, account_id, token, gmt_create, gmt_modified, avatar_url, bio) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio})")
	public void insertUser(User user);

	// 根据token查询用户信息
	@Select("SELECT * FROM USER WHERE token=#{token}")
	public User findByToken(@Param("token") String token);

	// 根据id查询用户信息
	@Select("SELECT * FROM USER WHERE id=${id}")
	public User getById(@Param("id") Integer id);
}
