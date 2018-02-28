package com.gy.biji.dao.psc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gy.biji.entity.GlobalMes;


public class GlobalPsc implements PreparedStatementCreator  {
	private GlobalMes gm;
	private String sql = "insert into biji.global(global_object) values(?)";
	public GlobalPsc(GlobalMes gm){
		this.gm = gm;
	}
	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setObject(1, gm);
		return ps;
	}

}
