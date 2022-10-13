package com.halal.halal.config;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;
	private final List<String> role;
	private final String username;
	
	public JwtResponse(String jwttoken,List<String> list, String username) {
		this.token = jwttoken;
		this.role=list;
		this.username=username;
	}

	public String getToken() {
		return this.token;
	}

	

	public List<String> getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}
	
	
	
}