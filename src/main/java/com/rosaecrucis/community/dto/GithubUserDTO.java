package com.rosaecrucis.community.dto;

// 用户信息传输对象，用于设置session里的用户信息
public class GithubUserDTO {

	private String name;
	private long id;
	private String bio;
	private String avatar_url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatarUrl() {
		return avatar_url;
	}

	public void setAvatarUrl(String avatar_url) {
		this.avatar_url = avatar_url;
	}
}
