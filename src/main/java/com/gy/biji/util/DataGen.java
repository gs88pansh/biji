package com.gy.biji.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gy.biji.entity.Poker;
import com.gy.biji.entity.TotalMes;

public class DataGen {
	public static List<Poker> generate() throws Exception{
		List<Poker>plist = new ArrayList<Poker>();
		for(int i=0;i<=51;i++) {
			int id = i;
			Poker puke = new Poker(id);
			plist.add(puke);
		}
		return plist;
	}
	
	
	/**
	 * 洗牌
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	
	public static void randomPokers(List<Poker>lastp) throws Exception {
		shuffle(lastp);
		cut(lastp);
	}
	
	public static void randomPokers(List<Poker>lastp,int cut) throws Exception {
		shuffle(lastp);
		cut(lastp,cut);
	}
	
	public static void shuffle(List<Poker>lastp) throws Exception{
		if(lastp.size() != 52) throw new Exception("牌数不等于52");
		List<Poker>newp = new ArrayList<Poker>();
		Random r = new Random();
		for(int i=52;i>0;i--) {
    		int m = r.nextInt(i);
    		newp.add(lastp.get(m));
    		lastp.remove(m);
    	}
		lastp.addAll(newp);
	}
	
	public static void cut(List<Poker>lastp) throws Exception{
		if(lastp.size() != 52) throw new Exception("牌数不等于52");
		List<Poker>newp = new ArrayList<Poker>();
		Random r = new Random();
		int index = r.nextInt(52);
		
		for(int i=0;i<52;i++) {
			int m = (index+i) % 52;
			newp.add(lastp.get(m));
		}
		
		lastp.clear();
		lastp.addAll(newp);
	}
	
	public static void cut(List<Poker>lastp,int cut) throws Exception{
		if(lastp.size() != 52) throw new Exception("牌数不等于52");
		List<Poker>newp = new ArrayList<Poker>();
		
		for(int i=0;i<52;i++) {
			int m = (cut+i) % 52;
			newp.add(lastp.get(m));
		}
		
		lastp.clear();
		lastp.addAll(newp);
	}

	public static void deal(List<Poker>lastp,List<TotalMes>tmlist) {
		
		for(int j=0;j<9;j++) {
			for(int k=0;k<tmlist.size();k++) {
				tmlist.get(k).orderedPukes.add(lastp.get(j*tmlist.size() + k));
			}
		}
		
		for(int k=0;k<tmlist.size();k++) {
			Compare.reorderDesc(tmlist.get(k).orderedPukes);
		}
		
	}

}
