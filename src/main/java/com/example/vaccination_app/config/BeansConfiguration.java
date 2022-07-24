package com.example.vaccination_app.config;

import com.example.vaccination_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BeansConfiguration {
	@Bean
	@Autowired
	public UserDetailsService userDetailsService(UserRepository repository) {
		return new UserDetailsServiceImpl(repository);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurerImpl();
	}
}
