package com.gy.biji.controller;

import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.GlobalMes;
import com.gy.biji.entity.Res;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.global.Global;
import com.gy.biji.handler.SystemWebSocketHandler;
import com.gy.biji.util.DataGen;

import net.sf.json.JSONObject;

@Controller
public class Prepare {
	
	@ResponseBody
	@RequestMapping(value={"/prepare"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> test1(HttpServletRequest request) throws Exception{
		
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		String user_name = (String) request.getSession().getAttribute(WEB.session_user);
		Integer room_number = (Integer) request.getSession().getAttribute(WEB.session_room_number);
		if(user_name == null)return new Res<GlobalMes>(WEB.res_error_msg,"你都没登陆，勺什么？","falied",null);
		if(room_number == null)return new Res<GlobalMes>(WEB.res_error_msg,"你都没进入房间，准备什么？","falied",null);
		
		/**
		 * 找到这一局 判断是否可以点击准备
		 * 
		 * 1、这一局完成了  orderedPukes。size() == 0;    *强制
		 * 2、你是这一局的人     
		 * 3、这一局至少已经有了一把历史局
		 */
		
		GlobalMes global = map.get(room_number);
		boolean b = true;
		for(int i=0;i<global.getThisMes().size();i++) {
			if(global.getThisMes().get(i).person_id.equals(user_name)) {
				b = false;
			}
		}
		if(b && global.allMes.size()>0 && (global.getThisMes().size() == 0||global.getThisMes().get(0).getOrderedPukes().size() == 0)) {
			int total_left = 0;
			for(int i=0;i<global.allMes.get(global.allMes.size()-1).size();i++) {
				if(global.allMes.get(global.allMes.size()-1).get(i).person_id.equals(user_name)) {
					total_left = global.allMes.get(global.allMes.size()-1).get(i).total_left;
				}
			}
			
			global.getThisMes().add(new TotalMes(null,user_name,total_left));
			
			if(global.getThisMes().size() == global.people_num) {
				DataGen.randomPokers(global.pile);
				DataGen.deal(global.pile, global.getThisMes());
			}
			
			Res<GlobalMes> res = new Res<GlobalMes>(WEB.res_global_msg,"准备成功","succeed",global);
			String str = JSONObject.fromObject(res).toString();
			TextMessage tm = new TextMessage(str);

			for(int i=0;i<global.getAllMes().get(0).size();i++) {
				String person_id = global.getAllMes().get(0).get(i).getPerson_id();
				if(!person_id.equals(user_name) && SystemWebSocketHandler.users.get(person_id)!=null) {
					WebSocketSession session = SystemWebSocketHandler.users.get(person_id);
					if(session.isOpen())
						try {
							SystemWebSocketHandler.users.get(person_id).sendMessage(tm);
						}catch(Exception e) {
							e.printStackTrace();
						}
				}
			}
			return res;
			
		}
		return new Res<GlobalMes>(WEB.res_error_msg,"不晓得怎么搞的，你失败了！","falied",null);
	}
	
}
