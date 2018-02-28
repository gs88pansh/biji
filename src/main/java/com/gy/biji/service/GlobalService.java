package com.gy.biji.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.biji.dao.GlobalDao;
import com.gy.biji.entity.GlobalMes;

@Service
public class GlobalService {

	
	@Autowired
	GlobalDao gdao;
	
	public int save(GlobalMes g) throws ClassNotFoundException, SQLException, IOException {
		return gdao.save(g);
	}

	public List<GlobalMes> get(String name) throws ClassNotFoundException, SQLException, IOException {
		return gdao.get(name);
	}

	public void saveUserGlobal(int Gid, String person_id) {
		gdao.saveUserGlobal(Gid,person_id);
	}
}
