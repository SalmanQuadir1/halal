package com.halal.halal.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.halal.halal.domain.User;
import com.halal.halal.repository.UserRepository;

@Service
@Transactional
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userrepo.findByUsername(username);
		if(user == null ){
			throw new UsernameNotFoundException(username);
		}
        return new UserDetailsImpl(user);
		 
	}

}
