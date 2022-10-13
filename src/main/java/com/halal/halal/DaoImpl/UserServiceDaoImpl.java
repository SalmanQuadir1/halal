package com.halal.halal.DaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.halal.halal.Dao.UserServiceDaoInterface;
import com.halal.halal.domain.User;

@Repository
public class UserServiceDaoImpl implements UserServiceDaoInterface {

	private EntityManager entitymanager;

	public EntityManager getEntitymanager() {
		return entitymanager;
	}

	@PersistenceContext
	public void setEntitymanager(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	@PersistenceContext
	EntityManager em;

	@Override
	public void updateDeviceToken(String username, String deviceToken) {
		System.out.println("update device token" + deviceToken);
		Query query = em.createQuery("Update User u set u.deviceToken = :deviceToken where u.username = :username")
				.setParameter("deviceToken", deviceToken).setParameter("username", username);
		query.executeUpdate();

	}

	@Override
	public List<String> getRolesOnUsername(String username) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("Select r.roleName from User u  JOIN u.roles r where u.username=:username")
				.setParameter("username", username);
		;
		return query.getResultList();
	}

	@Override
	public boolean logout(String deviceToken) {

		try {
			Query q = em.createQuery("Update  User u set u.deviceToken=null where u.deviceToken=:deviceToken")
					.setParameter("deviceToken", deviceToken);

			q.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public User findUserByUsername(String userName) {
		Query query = em.createQuery("Select u from User u where u.username=:userName").setParameter("userName",
				userName);

		try {
			return (User) query.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	public User updateUser(User user) {
		try {

			return em.merge(user);
		} catch (Exception ex) {
			return null;
		}

	}
	
	@Override
	public User findUserByEmail(String email) {
		Query query = em.createQuery("Select u from User u where u.email=:email").
				setParameter("email", email);
		try
		{
		return (User) query.getSingleResult();
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	@Override
	public User findUserByResetToken(String resetToken) {
		Query query = em.createQuery("Select u from User u where u.resetToken=:resetToken").
				setParameter("resetToken", resetToken);
		try
		{
			return (User) query.getSingleResult();
		}
		catch(Exception ex)
		{
			return null;
		}
	}
}
