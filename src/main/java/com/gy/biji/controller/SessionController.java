package com.gy.biji.controller;

import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.GlobalMes;
import com.gy.biji.entity.Res;
import com.gy.biji.global.Global;

@Controller
public class SessionController {

	
	@ResponseBody
	@RequestMapping(value={"/login1"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request,ModelMap model,
			@RequestParam String name){
		//request.getSession().setAttribute(WEB.session_user, name);
		return "succeed";
	}
	@ResponseBody
	@RequestMapping(value={"/get-global-mes"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> getGlobalMes(HttpServletRequest request,ModelMap model){

		String name = (String) request.getSession().getAttribute(WEB.session_user);
		if(name == null) {
			return new Res<GlobalMes>(WEB.res_error_msg,"你都没登陆，在这里则比弄怪的额！","failed-e",null);
		}
		Integer room_number = (Integer) request.getSession().getAttribute(WEB.session_room_number);
		if(room_number == null) {
			return new Res<GlobalMes>(WEB.res_error_msg,"你都没进入房间，在这里则比弄怪的额！","failed",null);
		}
		
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		GlobalMes global = map.get(room_number);

		
		return new Res<GlobalMes>(WEB.res_global_msg,"准备成功","succeed",global);
	}
	
}
