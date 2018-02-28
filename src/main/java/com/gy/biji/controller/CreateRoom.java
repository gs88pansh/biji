package com.gy.biji.controller;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.GlobalMes;
import com.gy.biji.entity.Res;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.global.Global;

@Controller
public class CreateRoom {

	@ResponseBody
	@RequestMapping(value={"/create-room"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> test1(HttpServletRequest request,ModelMap model,int people_number) throws Exception{
		
		
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		String user_name = (String) request.getSession().getAttribute(WEB.session_user);
		
		if(user_name == null) {
			return new Res<GlobalMes>(WEB.res_error_msg,"你还没登陆，你当我孬子？快去登陆去！！！","failed",null);
		}
		
		Random r = new Random();
		
		int room_number = 100000 + r.nextInt(100000);
		
		while(map.containsKey(room_number)) {
			room_number = 100000 + (room_number+1)%100000;
		}
		
		//固定16局
		GlobalMes global = new GlobalMes(people_number,room_number,16,user_name,System.currentTimeMillis());
		
		global.getThisMes().add(new TotalMes(null,user_name,0));
		
		map.put(room_number, global);
		
		request.getSession().setAttribute(WEB.session_room_number, room_number);
		
		return new Res<GlobalMes>(WEB.res_global_msg,"创建成功！","succeed",global);
	}
	
}
