package com.imageupload.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class ImageuploadApplication{

	public static void main(String[] args) {
		SpringApplication.run(ImageuploadApplication.class, args);
	}

}
