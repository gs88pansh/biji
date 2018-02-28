package com.gy.biji.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.GlobalMes;
import com.gy.biji.entity.Res;
import com.gy.biji.service.GlobalService;

@Controller
public class HistoryData {
	@Autowired
	private GlobalService gs;
	
	
	@ResponseBody
	@RequestMapping(value={"/history"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<List<GlobalMes>> login(HttpServletRequest request,ModelMap model) throws ClassNotFoundException, SQLException, IOException{
		String name = (String)request.getSession().getAttribute(WEB.session_user);
		if(name == null) {
			return new Res<List<GlobalMes>>(WEB.res_global_msg_list,"你没登陆","failed",null);
		}
		
		return new Res<List<GlobalMes>>(WEB.res_global_msg_list,name,"succeed",gs.get(name));
		
	}
}
