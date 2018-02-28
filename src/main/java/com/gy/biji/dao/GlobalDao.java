package com.gy.biji.dao;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gy.biji.dao.mapper.BlobMapper;
import com.gy.biji.dao.psc.GlobalPsc;
import com.gy.biji.entity.GlobalMes;

public class GlobalDao  extends JdbcDaoSupport{
	public List<GlobalMes> get(String name) throws SQLException, IOException, ClassNotFoundException {
		String sql = "select global_object,g.id from biji.user u,biji.global g,biji.user_global ug where u.name = ug.user_name and ug.global_id = g.id and u.name = ? order by ug.id desc limit 10";
		BlobMapper mapper = new BlobMapper();
		List<Blob>ul = getJdbcTemplate().query(sql, mapper,name);
		List<GlobalMes>gl = new ArrayList<GlobalMes>();
		for(int i=0;i<ul.size();i++) {
			Blob b = ul.get(i);
			InputStream is=b.getBinaryStream();
			BufferedInputStream bis=new BufferedInputStream(is);    //带缓冲区的流对象  
	        
	        byte[] buff=new byte[(int) b.length()];
	        bis.read(buff, 0, buff.length);
	        ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(buff));  
	        GlobalMes p=(GlobalMes)in.readObject();
	        gl.add(p);
		}
        return gl;
	}
	
	
	public int save(GlobalMes g) throws SQLException, IOException, ClassNotFoundException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new GlobalPsc(g), keyHolder);
		return keyHolder.getKey().intValue();
	}


	public void saveUserGlobal(int gid, String person_id) {
		String sql = "insert into biji.user_global(user_name,global_id)values(?,?)";
		getJdbcTemplate().update(sql,person_id,gid);
	}
}
