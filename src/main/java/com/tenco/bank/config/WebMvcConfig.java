package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthIntercepter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthIntercepter authIntercepter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authIntercepter).addPathPatterns("/account/**").addPathPatterns("/auth/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}

}
