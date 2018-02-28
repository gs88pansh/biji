package com.gy.biji.controller;

import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class JoinRoom {
	@ResponseBody
	@RequestMapping(value={"/join-room"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> test1(HttpServletRequest request,ModelMap model,int room_number) throws Exception{
		
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		String user_name = (String) request.getSession().getAttribute(WEB.session_user);
		
		if(user_name == null) 
			return new Res<GlobalMes>(WEB.res_error_msg,"你还没登陆，你当我孬子？快去登陆去！！！","failed",null);
		
		if(!map.containsKey(room_number)) 
			return new Res<GlobalMes>(WEB.res_error_msg,"老表哎，你输入的房间号不存在额！","failed",null);
		
		GlobalMes global = map.get(room_number);
		
		
		/**
		 * 
		 * 
		 * 第一次进入房间成功条件判定		-------------	判断 
		 * 									thisMes.size()!=global.people_num 
		 * 									&&
		 * 									global.getAllMes().size() == 0 
		 * 									&& 
		 * 									global.getThisMes()中不含此人
		 * 
		 * 重连成功的条件判定                         	-------------	判断
		 * 									1.未发拍，相当于进来就准备，除了在global.getThisMes()中不添加新的变量外不做任何处理
		 * 										thisMes.size() < global.people_num
		 * 										&&
		 * 										global.getThisMes()中含此人
		 * 									2.一发拍，前台需要判断global来显示相应的信息
		 * 										thisMes.size() < global.people_num
		 * 										&&
		 * 										global.getThisMes()中含此人
		 * 									3.allMes中含有，但是thisMes中没有，但是未点准备就离线了，现在重新连接，需要在thisMes中加上这个人，接下来的做法和第一次一样
		 * 										global.getThisMes()中不含此人
		 * 										&&
		 * 										global.getAllMes().size() > 0
		 * 										&&
		 * 										global.getAllMes().get(0)中含有此人
		 * 
		 */
		
		
		// thisMes 中 有这个人 ----> b
		
		boolean thisMesb = false;
		for(int i=0;i<global.getThisMes().size();i++) {
			if(global.getThisMes().get(i).person_id.equals(user_name)) {
				thisMesb = true;
				break;
			}
		}
		
		boolean allMesb = global.getAllMes().size()>0;
		if(allMesb) {
			allMesb = false;
			for(int i=0;i<global.getAllMes().get(0).size();i++) {
				if(global.getAllMes().get(0).get(i).person_id.equals(user_name)) {
					allMesb = true;
					break;
				}
			}
		}
		// 第一次 登陆的处理：
		if(global.getThisMes().size()<global.people_num && global.getAllMes().size() == 0 && !thisMesb){
			
			global.getThisMes().add(new TotalMes(null,user_name,0));
			
			if(global.getThisMes().size() == global.people_num) {
				DataGen.randomPokers(global.pile);
				DataGen.deal(global.pile, global.getThisMes());
			}
			
			Res<GlobalMes> res = new Res<GlobalMes>(WEB.res_global_msg,"加入成功","succeed",global);
			String str = JSONObject.fromObject(res).toString();
			TextMessage tm = new TextMessage(str);
			
			for(int i=0;i<global.getThisMes().size();i++) {
				String person_id = global.getThisMes().get(i).getPerson_id();
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
			
			request.getSession().setAttribute(WEB.session_room_number, room_number);
			return res;
		}
		
		// 重连 ------- 情况1 情况2合并
		if(allMesb||thisMesb) {

			String str = JSONObject.fromObject(new Res<String>(WEB.res_user_reconnected,"那呆子重新连接了","succeed",user_name)).toString();
			TextMessage tm = new TextMessage(str);
			
			for(int i=0;i<global.getThisMes().size();i++) {
				String person_id = global.getThisMes().get(i).getPerson_id();
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

			request.getSession().setAttribute(WEB.session_room_number, room_number);
			return new Res<GlobalMes>(WEB.res_global_msg,"我终于重新来了","succeed",global);
		}
			
		return new Res<GlobalMes>(WEB.res_error_msg,"老表哎，你太目着蛮，人都齐了你才过来！","failed",null);
	}
}
