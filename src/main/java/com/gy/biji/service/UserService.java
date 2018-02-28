package com.gy.biji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.biji.dao.UserDao;
import com.gy.biji.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao udao;
	
	public boolean saveUser(String phone,String password,String name) {
		User u = new User();
		u.phone = phone;
		u.password = password;
		u.name = name;
		udao.save(u);
		return true;
	}
	
	public User getUser(String name) {
		return udao.get(name);
	}
	
	public User getUserByPhone(String phone) {
		return udao.getByPhone(phone);
	}
}
