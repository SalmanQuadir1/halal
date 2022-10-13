package com.halal.halal.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import com.halal.halal.repository.UserRepository;


@Service
@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userDetailsService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.halal.halal.domain.User appUser = userDetailsService.findByUsername(username);
				//loadUserByUsername(username);
		
		if (appUser != null) {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			return new User(appUser.getUsername(),appUser.getPassword(),grantedAuthorities);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}