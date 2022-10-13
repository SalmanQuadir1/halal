package com.halal.halal.Dao;

import java.util.List;

import com.halal.halal.domain.User;

public interface UserServiceDaoInterface {

	public void updateDeviceToken(String username, String deviceToken);
	public List<String> getRolesOnUsername(String username);
	public boolean logout(String username);
	public User findUserByUsername(String userName);
	public User updateUser(User user);
	public User findUserByEmail(String email);
	public User findUserByResetToken(String resetToken);
}
