package com.gy.biji.entity;

import java.io.Serializable;

import com.gy.biji.constance.Constance;

public class Poker  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private int id;
	private int index;
	private int color;
	
	private String indexStr;
	
	private String colorStr;
	
	public Poker(int id) throws Exception {
		if(id >= 52 || id < 0) throw new Exception("worng puke");
		this.id = id;
		this.index = id/4;
		this.color = id%4;
		this.setColorStr(Constance.colors[color]);
		this.setIndexStr(Constance.puStrs[index]);
	}

	public Poker(int id,int index,int color) {
		this.id = id;

		this.index = index;
		this.color = color;
		
		this.setColorStr(Constance.colors[color]);
		this.setIndexStr(Constance.puStrs[index]);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return indexStr+" "+colorStr;
	}

	public String getIndexStr() {
		return indexStr;
	}

	public void setIndexStr(String indexStr) {
		this.indexStr = indexStr;
	}

	public String getColorStr() {
		return colorStr;
	}

	public void setColorStr(String colorStr) {
		this.colorStr = colorStr;
	}
	
	
}
