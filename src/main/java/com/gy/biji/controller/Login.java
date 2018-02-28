package com.gy.biji.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.entity.User;
import com.gy.biji.handler.SystemWebSocketHandler;
import com.gy.biji.service.UserService;
import com.gy.biji.test.Test1;
import com.gy.biji.util.BiFen;
import com.gy.biji.util.PhoneFormatCheckUtil;

import net.sf.json.JSONArray;



@Controller
public class Login {
	
	@Autowired
	private UserService us;
	
	@ResponseBody
	@RequestMapping(value={"/r"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request,ModelMap model,
			@RequestParam String phone,
			@RequestParam String password,
			@RequestParam String name){
		
		if(!PhoneFormatCheckUtil.isPhoneLegal(phone)) {
			return "手机错误号格式错误！";
		}
		
		if(us.getUser(name) != null) {
			return "用户名重复了";
		}
		
		if(us.getUser(phone) != null) {
			return "手机号重复了";
		}
		
		if(password.length() < 6) {
			return "密码大于6位";
		}
		
		if(us.saveUser(phone, password, name)) {
			request.getSession().setAttribute(WEB.session_user, name);
			return "succeed";
		}
		return "failed";
	}

	@ResponseBody
	@RequestMapping(value={"/l"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request,ModelMap model,
			@RequestParam String phone,
			@RequestParam String password){
		
		if(!PhoneFormatCheckUtil.isPhoneLegal(phone)) {
			return "手机错误号格式错误！";
		}
		User u = us.getUserByPhone(phone);
		if(u == null) {
			return "手机号不存在！";
		}
		if(phone.equals(u.phone) && password.equals(u.password)) {
			request.getSession().setAttribute(WEB.session_user, u.name);
			return "succeed";
		}
		
		return "密码不对！";
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value={"/test1"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String test1(HttpServletRequest request,ModelMap model) throws IOException{
		SystemWebSocketHandler.sendMessageToUsers(new TextMessage("nimade"));
		return "failed";
	}
	
	@ResponseBody
	@RequestMapping(value={"/test2"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String test2(HttpServletRequest request,ModelMap model) throws Exception{

		List<TotalMes> tmp = Test1.genToTalMess(Test1.generateRadomArray(5));
		
		BiFen.bifen(tmp);
		
		JSONArray jsonArray = JSONArray.fromObject(tmp);
		System.out.println(jsonArray.toString());
		
		SystemWebSocketHandler.sendMessageToUsers(new TextMessage(jsonArray.toString()));
		
		return tmp.toString();
	}
}
