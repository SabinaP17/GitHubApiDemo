package com.example.githubapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.githubapidemo")
public class GitHubApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubApiDemoApplication.class, args);
	}
}
