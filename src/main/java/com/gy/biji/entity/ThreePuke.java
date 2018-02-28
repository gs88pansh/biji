package com.gy.biji.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gy.biji.constance.Constance;

public class ThreePuke  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID =5L;

	public List<Poker> pukes;
	
	public int typeInt;
	
	public String typeStr;

	public ThreePuke(List<Poker>pukes) throws Exception {
		if(pukes.size()!=3) throw new Exception("numbers of pukes must be 3");
		reorder(pukes);
		this.pukes = pukes;
		this.typeInt = getType(pukes);
		this.typeStr = Constance.typeStr[typeInt];
	}
	
	public List<Poker> getPukes() {
		return pukes;
	}

	public void setPukes(List<Poker> pukes) {
		this.pukes = pukes;
	}

	public int getTypeInt() {
		return typeInt;
	}

	public void setTypeInt(int typeInt) {
		this.typeInt = typeInt;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	@Override
	public String toString() {
		return pukes.toString();
	}
	
	private int getType(List<Poker>p) {
		//i0<=i1<=i2
		
		int i0 = p.get(0).getIndex();
		int i1 = p.get(1).getIndex();
		int i2 = p.get(2).getIndex();
		
		int c0 = p.get(0).getColor();
		int c1 = p.get(1).getColor();
		int c2 = p.get(2).getColor();
		
		// 三个头
		if(i0 == i1 && i0 == i2) return Constance.san_ge_tou;
		
		// 顺青
		if(((i0+1 == i1 && i1+1 == i2) || (i0 == 0 && i1 == 1 && i2 == 12)) && (c0 == c1 && c0 == c2)) return Constance.shun_qing;
	
		// 金花
		if(c0 == c1 && c0 == c2) return Constance.jin_hua;
		
		// 拖拉机
		if((i0+1 == i1 && i1+1 == i2) || (i0 == 0 && i1 == 1 && i2 == 12)) return Constance.tuo_la_ji;
		
		// 对子 
		if(i0 == i1 || i0 == i2 || i1 == i2) return Constance.dui_zi;
		
		return Constance.dan_zhi_zi;
		
	}
	
	private List<Poker> reorder(List<Poker>p) {
        Collections.sort(p,new Comparator<Poker>(){  
            public int compare(Poker arg0, Poker arg1) {  
                int id0 = arg0.getId();
                int id1 = arg1.getId();
                if(id0 > id1) return 1;
                if(id0 < id1) return -1;
                else return 0;
            }  
        });
		return p;
	}
	
}
