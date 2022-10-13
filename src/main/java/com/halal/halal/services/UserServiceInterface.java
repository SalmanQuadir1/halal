package com.halal.halal.services;

import java.util.List;

import com.halal.halal.domain.User;

public interface UserServiceInterface {

	public List<String> getRolesOnUsername(String username);

	public void updateDeviceToken(String username, String deviceToken);

	public boolean logout(String username);

	public User findUserByUsername(String userName);

	public User updateUser(User user);

	public User findUserByEmail(String email);

	public User findUserByResetToken(String resetToken);
}
