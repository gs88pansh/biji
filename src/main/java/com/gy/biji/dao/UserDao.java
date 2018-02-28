package com.gy.biji.dao;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.gy.biji.dao.mapper.UserMapper;
import com.gy.biji.entity.User;

public class UserDao extends JdbcDaoSupport{
	
	public User get(String name) {
		String sql = "select * from biji.user where name = ?";
		UserMapper mapper = new UserMapper();
		List<User>ul = getJdbcTemplate().query(sql, mapper,name);
		return ul.size()>0?ul.get(0):null;
	}
	
	public User getByPhone(String phone) {
		String sql = "select * from biji.user where phone = ?";
		UserMapper mapper = new UserMapper();
		List<User>ul = getJdbcTemplate().query(sql, mapper,phone);
		return ul.size()>0?ul.get(0):null;
	}
	
	public void save(User user) {
		String sql = "insert into biji.user(name,phone,password) values(?,?,?)";
		getJdbcTemplate().update(sql, user.name,user.phone,user.password);
	}
	
	
}
