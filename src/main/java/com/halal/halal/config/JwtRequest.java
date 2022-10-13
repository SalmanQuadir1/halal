package com.halal.halal.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	private String deviceToken;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public JwtRequest(String username, String password, String deviceToken) {
		super();
		this.username = username;
		this.password = password;
		this.deviceToken = deviceToken;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public String toString() {
		return "JwtRequest [username=" + username + ", password=" + password + ", deviceToken=" + deviceToken + "]";
	}
	
	
	
}