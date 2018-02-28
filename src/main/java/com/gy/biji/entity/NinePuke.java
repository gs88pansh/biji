package com.gy.biji.entity;

import java.io.Serializable;
import java.util.List;

import com.gy.biji.util.Compare;

public class NinePuke  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	public List<ThreePuke> tpukes; // small ---> big
//	
//	public ThreePuke first;
//	
//	public ThreePuke second;
//	
//	public ThreePuke third;
	
	public boolean giveup;
	
	public NinePuke(List<ThreePuke> tpukes,boolean is_giveup) throws Exception {
		if(!is_giveup) {
			if(tpukes.size() != 3) throw new Exception("");
			Compare.reorderThreePuke(tpukes);
			this.tpukes = tpukes;
		}
		this.giveup = is_giveup;
	}

	public List<ThreePuke> getTpukes() {
		return tpukes;
	}

	public void setTpukes(List<ThreePuke> tpukes) {
		this.tpukes = tpukes;
	}

	public boolean isGiveup() {
		return giveup;
	}

	public void setGiveup(boolean is_giveup) {
		this.giveup = is_giveup;
	}
	

	@Override
	public String toString() {
		return tpukes.toString();
	}
	
	
}
