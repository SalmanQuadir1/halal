package com.halal.halal.serviceImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.halal.halal.domain.Role;
import com.halal.halal.domain.User;


public class UserDetailsImpl implements UserDetails {

	
	
		private static final long serialVersionUID = 1L;
		private User user;
		
		
		public UserDetailsImpl(User user) {
			super();
			this.user = user;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		  Collection<GrantedAuthority>  authorities= new HashSet<GrantedAuthority>();
		  List<Role> roles = user.getRoles();
		   for (Role role : roles) {
			   authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		    }
		   return authorities;
		}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
	
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
