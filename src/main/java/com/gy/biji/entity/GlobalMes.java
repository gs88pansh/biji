package com.gy.biji.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gy.biji.util.DataGen;

public class GlobalMes  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int number_of_games;
	
	public int number_th;
	
	public String user_name;
	
	public List<Poker> pile;
	
	public List<List<TotalMes>> allMes;
	
	public List<TotalMes> thisMes;
	
	public Integer people_num;
	
	public Integer room_id;
	
	public String dateStr;
	
	public long create_time_stamp;
	
	
	public List<Poker> getPile() {
		return pile;
	}

	public void setPile(List<Poker> pile) {
		this.pile = pile;
	}

	public List<List<TotalMes>> getAllMes() {
		return allMes;
	}

	public void setAllMes(List<List<TotalMes>> allMes) {
		this.allMes = allMes;
	}

	public List<TotalMes> getThisMes() {
		return thisMes;
	}

	public void setThisMes(List<TotalMes> thisMes) {
		this.thisMes = thisMes;
	}

	public Integer getPeople_num() {
		return people_num;
	}

	public void setPeople_num(Integer people_num) {
		this.people_num = people_num;
	}

	public Integer getRoom_id() {
		return room_id;
	}

	public void setRoom_id(Integer room_id) {
		this.room_id = room_id;
	}
	
	public int getNumber_of_games() {
		return number_of_games;
	}

	public void setNumber_of_games(int number_of_games) {
		this.number_of_games = number_of_games;
	}

	public int getNumber_th() {
		return number_th;
	}

	public void setNumber_th(int number_th) {
		this.number_th = number_th;
	}
	
	public String getUser_Name() {
		return user_name;
	}

	public void setUser_Name(String name) {
		this.user_name = name;
	}

	public GlobalMes(int people_num,int room_id,int number_of_games,String user_name, long l) throws Exception{
		
		this.room_id = room_id;
		this.people_num = people_num;
		this.number_of_games = number_of_games;
		
		number_th = 0;
		pile = DataGen.generate();
		allMes = new ArrayList<List<TotalMes>>();
		thisMes = new ArrayList<TotalMes>();
		
		this.user_name = user_name;
		this.create_time_stamp = l;
		
	}
}
