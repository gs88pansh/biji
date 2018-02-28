package com.gy.biji.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.gy.biji.constance.WEB;
import com.gy.biji.entity.GlobalMes;
import com.gy.biji.entity.NinePuke;
import com.gy.biji.entity.Poker;
import com.gy.biji.entity.Res;
import com.gy.biji.entity.ThreePuke;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.global.Global;
import com.gy.biji.handler.SystemWebSocketHandler;
import com.gy.biji.service.GlobalService;
import com.gy.biji.util.BiFen;
import com.gy.biji.util.Compare;

import net.sf.json.JSONObject;

@Controller
public class SubmitNinePuke {
	
	@Autowired
	private GlobalService gs;
	
	@ResponseBody
	@RequestMapping(value={"/submit"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> submitNinePuke(HttpServletRequest request,ModelMap model,int[]pokerIds) throws Exception{
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		String user_name = (String) request.getSession().getAttribute(WEB.session_user);
		Integer room_number = (Integer) request.getSession().getAttribute(WEB.session_room_number);
		
		GlobalMes global = map.get(room_number);
		
		if(user_name == null) {
			return new Res<GlobalMes>(WEB.res_error_msg,"你还没登陆，你当我孬子？快去登陆去！！！","failed",null);
		}

		if(pokerIds.length!=9) return new Res<GlobalMes>(WEB.res_error_msg,"必须提交九张牌，哥哥哎！！！","failed",null);

		NinePuke np = getNinePuke(pokerIds);
		
		if(np == null) {
			return new Res<GlobalMes>(WEB.res_error_msg,"你玩倒顿子？得我二果蛮","failed",null);
		}
		
		int submit_num = 0;
		
		for(int i=0;i<global.getThisMes().size();i++) {
			if(global.getThisMes().get(i).getPerson_id().equals(user_name)) {
				global.getThisMes().get(i).setPukes(np);
			}
			
			if(global.getThisMes().get(i).getPukes() != null) {
				submit_num ++;
			}
		}
		
		if(submit_num == global.people_num) {
			
			global.number_th ++;
			
			BiFen.bifen(global.getThisMes());
			
			global.allMes.add(global.thisMes);
			
			
			if(global.number_th == global.number_of_games) {
				List<TotalMes>tl = global.thisMes;
				global.pile = null;
				global.thisMes = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				global.dateStr = sdf.format(new Date());
				int id = gs.save(global);
				List<TotalMes> ml =global.allMes.get(global.allMes.size()-1);
				for(int i=0;i<ml.size();i++) {
					gs.saveUserGlobal(id,ml.get(i).person_id);
				}
				map.remove(room_number);
				global.thisMes = tl;
			}
			
			List<TotalMes> thisMes = global.getThisMes();
			global.setThisMes(new ArrayList<TotalMes>());
			
			String str = JSONObject.fromObject(new Res<GlobalMes>(WEB.res_global_msg,"这吊人准备好了","succeed",global)).toString();
			TextMessage tm = new TextMessage(str);
			
			for(int i=0;i<thisMes.size();i++) {
				String person_id = thisMes.get(i).getPerson_id();
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

			
			new Res<GlobalMes>(WEB.res_global_msg,"这吊人准备好了","succeed",global);
		}
		
		String str = JSONObject.fromObject(new Res<String>(WEB.res_user_prepare,"这吊人准备好了","succeed",user_name)).toString();
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
		
		return new Res<GlobalMes>(WEB.res_global_msg,"提交成功！","succeed",global);
	}
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value={"/giveup"},method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public Res<GlobalMes> submitNinePuke(HttpServletRequest request) throws Exception{
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		
		String user_name = (String) request.getSession().getAttribute(WEB.session_user);
		Integer room_number = (Integer) request.getSession().getAttribute(WEB.session_room_number);
		
		GlobalMes global = map.get(room_number);
		
		
		
		int submit_num = 0;
		
		for(int i=0;i<global.getThisMes().size();i++) {
			if(global.getThisMes().get(i).getPerson_id().equals(user_name)) {
				global.getThisMes().get(i).setPukes(new NinePuke(null,true));
			}
			
			if(global.getThisMes().get(i).getPukes() != null) {
				submit_num ++;
			}
		}
		
		if(submit_num == global.people_num) {
			global.number_th ++;
			BiFen.bifen(global.getThisMes());
			global.allMes.add(global.thisMes);
			if(global.number_th == global.number_of_games) {
				List<TotalMes>tl = global.thisMes;
				global.pile = null;
				global.thisMes = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				global.dateStr = sdf.format(new Date());
				int id = gs.save(global);
				List<TotalMes> ml =global.allMes.get(global.allMes.size()-1);
				for(int i=0;i<ml.size();i++) {
					gs.saveUserGlobal(id,ml.get(i).person_id);
				}
				map.remove(room_number);
				global.thisMes = tl;
			}
			
			List<TotalMes> thisMes = global.getThisMes();
			global.setThisMes(new ArrayList<TotalMes>());
			
			String str = JSONObject.fromObject(new Res<GlobalMes>(WEB.res_global_msg,"这吊人准备好了","succeed",global)).toString();
			TextMessage tm = new TextMessage(str);
			
			for(int i=0;i<thisMes.size();i++) {
				String person_id = thisMes.get(i).getPerson_id();
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
			new Res<GlobalMes>(WEB.res_global_msg,"这吊人准备好了","succeed",global);
		}
		
		String str = JSONObject.fromObject(new Res<String>(WEB.res_user_prepare,"这吊人准备好了","succeed",user_name)).toString();
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
		
		return new Res<GlobalMes>(WEB.res_global_msg,"提交成功！","succeed",global);
	}
	
	
	
	
	
	public static NinePuke getNinePuke(int[] A) throws Exception {
		
		List<Poker>pl = new ArrayList<Poker>();
		List<ThreePuke>tpl = new ArrayList<ThreePuke>();
		
		NinePuke np = null;
		
		for(int i=0;i<A.length;i++) {
			Poker p = new Poker(A[i]);
			pl.add(p);
			if(pl.size() == 3) {
				ThreePuke tp = new ThreePuke(pl);
				pl = new ArrayList<Poker>(); 
				tpl.add(tp);
				
				
				if(tpl.size() == 3) {

					if(Compare.compare(tpl.get(0), tpl.get(1))!=-1 || Compare.compare(tpl.get(1), tpl.get(2))!=-1) {
						return null;
					}
					
					return new NinePuke(tpl,false);
				}
			}
		}

		return np;
	}
	
	public static void main(String []z) throws Exception {
		getNinePuke(new int[] {19,13,9,28,27,20,41,37,29});
	}
	
}
