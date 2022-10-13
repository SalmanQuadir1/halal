package com.halal.halal.servicesImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.halal.halal.Dao.UserServiceDaoInterface;
import com.halal.halal.domain.User;
import com.halal.halal.services.UserServiceInterface;
@Service
@Primary
@Transactional
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	UserServiceDaoInterface userdao;
	
	@Override
	public void updateDeviceToken(String username, String deviceToken) {
		userdao.updateDeviceToken(username, deviceToken);
		
	}

	@Override
	public List<String> getRolesOnUsername(String username) {
		// TODO Auto-generated method stub
		return userdao.getRolesOnUsername(username);
	}
	
	@Override
	public boolean logout(String deviceToken) {
		return userdao.logout(deviceToken);
	}

	@Override
	public User findUserByUsername(String userName) {
		// TODO Auto-generated method stub
		return userdao.findUserByUsername(userName);
	}

	@Override
	public User updateUser(User user) {
		return userdao.updateUser(user);
		
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userdao.findUserByEmail(email);
	}

	@Override
	public User findUserByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		return userdao.findUserByResetToken(resetToken);
	}

}
