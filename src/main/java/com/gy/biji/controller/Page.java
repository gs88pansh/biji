package com.gy.biji.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gy.biji.constance.WEB;
@Controller
public class Page {
	@RequestMapping(value={"/page1"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String test1(HttpServletRequest request,ModelMap model){
		String name = (String) request.getSession().getAttribute(WEB.session_user);
		model.addAttribute("person_id", name);
		return "page1";
	}
	
	@RequestMapping(value={"/hall"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String hall(HttpServletRequest request,ModelMap model){
		return "hall";
	}
	
	@RequestMapping(value={"/e"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String e(HttpServletRequest request,ModelMap model){
		return "login";
	}
	
	@RequestMapping(value={"/f"},method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String f(HttpServletRequest request,ModelMap model){
		return "register";
	}
}
