package com.rosaecrucis.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBoot入口类
@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		// 传入入口类类别，以及参数，启动SpringBoot
		SpringApplication.run(CommunityApplication.class, args);
	}

}
