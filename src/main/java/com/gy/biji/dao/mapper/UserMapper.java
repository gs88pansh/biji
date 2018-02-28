package com.gy.biji.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gy.biji.entity.User;


public class UserMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User t = new User();
		t.id = rs.getInt("id");
		t.name = rs.getString("name");
		t.phone = rs.getString("phone");
		t.password = rs.getString("password");
		return t;
	}

}
