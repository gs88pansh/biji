package com.gy.biji.entity;

import java.io.Serializable;

public class XiPaiFenMes  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;

	public int point;
	
	public boolean win;
	
	public String note;
	
	public XiPaiFenMes(int point,boolean win, String note){
		
		this.point = point;
		this.win = win;
		this.note = note;
		
	}
	
	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return point+"";
	}
}
