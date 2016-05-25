package com.expenses.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.expenses.security.JwtAuthenticationFilter;
import com.expenses.service.UserService;

@Configuration
@EnableWebSecurity
@Order(2)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
	UserService userService;

	public APISecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().and().anonymous().and().servletApi().and().headers().cacheControl().disable().and()
				.authorizeRequests()
				.antMatchers("/api/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(tokenBasedAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(tokenBasedAuthenticationFilter());
		registration.addUrlPatterns("/api/**");
		registration.setOrder(4);
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("jwtFilter");
		return registration;
	}

	@Override
	public void init(WebSecurity web) throws Exception {
		super.init(web);
		web.ignoring().antMatchers("/login**", "/register**");
	}

	@Bean
	public JwtAuthenticationFilter tokenBasedAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	@Override
	public UserService userDetailsService() {
		return userService;
	}

}
