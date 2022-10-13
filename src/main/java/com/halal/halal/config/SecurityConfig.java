package com.halal.halal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.halal.halal.repository.UserRepository;
import com.halal.halal.serviceImpl.UserDetailsImpl;
import com.halal.halal.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity( securedEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserRepository userrepo;
	 
	@Autowired
    UserDetailsServiceImpl userdetailservice;


		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception{
			 auth.userDetailsService(userdetailservice).passwordEncoder(passwordEncoder());
			
			
		}		
		
	@Override
	public void configure(HttpSecurity http) throws Exception{
			System.out.println("inside configgggggg");
		 http
		     .headers().disable()
		    .authorizeRequests()
		// .antMatchers("/settings/**").hasRole("ADMIN")
		   .antMatchers("/login","/css/**","/js/**","/images/**","/bootstrap/**","/webfonts/**").permitAll()
		    .anyRequest().authenticated()
		    
		  .and()
		     .formLogin()
		       .loginPage("/login")
		       .loginProcessingUrl("/login")
		       .successHandler(authHandler())
		       .usernameParameter("username")
		       .permitAll()
		         .and()
		     .logout()
		        .logoutSuccessUrl("/login?logout")
		        .permitAll();
	}
	
	 @Bean
	    public PasswordEncoder passwordEncoder(){
	        PasswordEncoder encoder = new BCryptPasswordEncoder();
	        return encoder;
	    }
		@Bean
		public AuthenticationSuccessHandler authHandler(){
		    return new AuthenticationHandler();
		}

}



