package com.example.vaccination_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public static final PasswordEncoder PASSWORD_ENCODER = NoOpPasswordEncoder.getInstance();
	public static final int ROLE_ADMIN_ID = 1;
	public static final int ROLE_USER_ID = 2;
	public static final String[] ADMIN_ENDPOINTS = {"/approved/", "/approved/**", "/booking/list",
													"/admin/", "/admin/**","answers/get-answers" };

	public final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(PASSWORD_ENCODER);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/", "/login")
				.permitAll()
				.antMatchers(ADMIN_ENDPOINTS)
				.hasRole("ADMIN")
				.antMatchers("/notification/")
				.hasAnyRole("USER", "ADMIN")
				.antMatchers("/booking/new")
				.hasRole("USER")
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", false)
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
				.csrf();
	}
}
