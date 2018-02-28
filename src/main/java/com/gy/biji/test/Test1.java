package com.gy.biji.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gy.biji.entity.NinePuke;
import com.gy.biji.entity.Poker;
import com.gy.biji.entity.ThreePuke;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.util.BiFen;

public class Test1 {

	public static void main(String[] args) throws Exception {
		int A[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
		
		int B[] = new int[] {0,4,8,12,16,20,24,28,32,1,2,3,5,6,7,9,10,11,44,45,46,48,49,13,43,42,41};
		
		int C[] = new int[] {49,1,9,14,18,22,23,27,31,0,4,12,51,47,39,42,34,30,2,3,5,6,7,8,10,11,13};
		
		int D[] = new int[] {};
		
		int E[] = new int[] {};
		
		List<TotalMes> tmp = genToTalMess(generateRadomArray(5));
		
		BiFen.bifen(tmp);
		
		System.out.println(tmp);
		
	}

	
	public static List<TotalMes> genToTalMess(int[] A) throws Exception {
		
		List<Poker>pl = new ArrayList<Poker>();
		List<ThreePuke>tpl = new ArrayList<ThreePuke>();
		List<NinePuke>npl = new ArrayList<NinePuke>();
		List<TotalMes>tmpl = new ArrayList<TotalMes>();
		
		for(int i=0;i<A.length;i++) {
			Poker p = new Poker(A[i]);
			pl.add(p);
			if(pl.size() == 3) {
				ThreePuke tp = new ThreePuke(pl);
				tpl.add(tp);
				
				if(tpl.size() == 3) {
					NinePuke np = new NinePuke(tpl,false);
					npl.add(np);
					tpl = new ArrayList<ThreePuke>();
				}
				
				pl = new ArrayList<Poker>();
			}
		}
		
		for(int i=0;i<npl.size();i++) {
			tmpl.add(new TotalMes(npl.get(i),"person_"+i,0));
		}
		
		tmpl.get(2).getPukes().setGiveup(true);
		tmpl.get(3).getPukes().setGiveup(true);
		
		System.out.println(tmpl);
		
		return tmpl;
	}
	
	public static int[] generateRadomArray(int people) throws Exception{
		if(people >= 6) throw new Exception("人数小于6人");
		
		int A[] = new int[people*9];
		
		Random r = new Random();
		
		List<Integer>il = new ArrayList<Integer>();
		
		for(int i=0;i<52;i++) {
			il.add(i);
		}
		
		for(int i=52;i>52-(people*9);i--) {
    		int m = r.nextInt(i);
    		A[52-i] = il.get(m);
    		il.remove(m);
    	}
		
		return A;
	}
	
}
