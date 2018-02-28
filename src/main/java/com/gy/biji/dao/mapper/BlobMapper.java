package com.gy.biji.dao.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class BlobMapper  implements RowMapper<Blob>{

	@Override
	public Blob mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getBlob("global_object");
	}
	
}
