package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		authenticationMgr.inMemoryAuthentication().withUser("user").password(encoder.encode("password")).roles("USER")
				.and().withUser("target").password(encoder.encode("password")).roles("USER", "ADMIN");
	}
	

	
	@Autowired
    DataSource dataSource;

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.jdbcAuthentication().usersByUsernameQuery("select username,password, enabled from users where username=?").authoritiesByUsernameQuery("select username, authority from authorities where username=?"). passwordEncoder(new BCryptPasswordEncoder() ).dataSource(dataSource);

    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
				.antMatchers(HttpMethod.POST).hasRole("ADMIN").antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
				.anyRequest().authenticated().and().csrf().disable().headers().frameOptions().disable();

	}

}