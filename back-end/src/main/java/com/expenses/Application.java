package com.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.expenses.config.JwtConfigurer;
import com.expenses.security.JwtAuthenticationFilter;

@EnableAutoConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public FilterRegistrationBean jwtFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new JwtAuthenticationFilter());
//		registrationBean.addUrlPatterns("/api/*");
//
//		return registrationBean;
//	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}

	@Bean
	public JwtConfigurer jwtConfigurer() {
		return new JwtConfigurer();
	}
}
