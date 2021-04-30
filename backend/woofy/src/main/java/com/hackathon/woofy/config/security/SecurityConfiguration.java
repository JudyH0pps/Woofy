package com.hackathon.woofy.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

//import 생략

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	 private final JwtTokenProvider jwtTokenProvider;
	
	 @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	 }
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	     http
	         .httpBasic().disable()
	         .csrf().disable()
	         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	         .and()
	             .authorizeRequests()
	                 .antMatchers(HttpMethod.POST, 
	                		 "/api/v1/parent", 
	                		 "/api/v1/child", 
	                		 "/api/v1/signin", 
	                		 "/api/v1/auth/*").permitAll()
	                 .anyRequest().hasRole("USER")
	         .and()
	             .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	
	 }
}