package com.gy.biji.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TotalMes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;

	public String person_id;
	
	public NinePuke pukes;
	
	public List<BiFenMes> biFenDetail;
	
	public List<XiPaiFenMes> xiPaiFenDetail;
	
	//Desc
	public List<Poker> orderedPukes;
	
	// 总共计算和
	public int total;
	
	// 余下
	public int total_left;
	
	public String note;

	
	public TotalMes(NinePuke pukes,String person_id,int total_left){
		
		this.pukes = pukes;
		
		this.person_id = person_id;
		
		this.biFenDetail = new ArrayList<BiFenMes>();
		
		this.xiPaiFenDetail = new ArrayList<XiPaiFenMes>();
		
		this.orderedPukes = new ArrayList<Poker>();
		
		this.total = 0;
		
		this.total_left = total_left;
		
	}
	
	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public NinePuke getPukes() {
		return pukes;
	}

	public void setPukes(NinePuke pukes) {
		this.pukes = pukes;
	}

	public List<BiFenMes> getBiFenDetail() {
		return biFenDetail;
	}

	public void setBiFenDetail(List<BiFenMes> biFenDetail) {
		this.biFenDetail = biFenDetail;
	}

	public List<XiPaiFenMes> getXiPaiFenDetail() {
		return xiPaiFenDetail;
	}

	public void setXiPaiFenDetail(List<XiPaiFenMes> xiPaiFenDetail) {
		this.xiPaiFenDetail = xiPaiFenDetail;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal_left() {
		return total_left;
	}

	public void setTotal_left(int total_left) {
		this.total_left = total_left;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	

	public List<Poker> getOrderedPukes() {
		return orderedPukes;
	}

	public void setOrderedPukes(List<Poker> orderedPukes) {
		this.orderedPukes = orderedPukes;
	}

	@Override
	public String toString() {
		String str = "";
		
		str += person_id + " " + (pukes.isGiveup()?"弃牌":"") + "\n";
		
		str += pukes.toString() + "\n";
		
		str += "比分：" + biFenDetail.toString() + "，喜牌分："+ xiPaiFenDetail.toString() + "，本局：" + total + "，累计：" + total_left + "\n";
		
		return str;
	}
	
	
	
	
}
