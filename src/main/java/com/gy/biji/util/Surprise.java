package com.gy.biji.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gy.biji.constance.Constance;
import com.gy.biji.entity.NinePuke;
import com.gy.biji.entity.Poker;
import com.gy.biji.entity.ThreePuke;

public class Surprise {
	
	public static List<Integer> judgeSurp(NinePuke np){
		
		List<Integer>res = new ArrayList<Integer>();
		
		if(np.isGiveup()) return res;
		
		List<ThreePuke> pukes = np.getTpukes();
		
		res = bigSurp(pukes);
		
		if(res.size() > 0)return res;
		
		res = smallSurp(pukes);
		
		return res;
	}
	
	
	private static List<Integer> smallSurp(List<ThreePuke> pukes) {
		List<Integer> res = new ArrayList<Integer>();
		
		ThreePuke first = pukes.get(0);
		ThreePuke second = pukes.get(1);
		ThreePuke third = pukes.get(2);
		
		List<Poker> ninePukes = new ArrayList<Poker>();
		
		for(int i=0;i<pukes.size();i++) {
			ninePukes.addAll(pukes.get(i).getPukes());
		}
		
		reorder(ninePukes);
		
		// quan_hei
		boolean quan_hei = true;
		for(int i=0;i<ninePukes.size();i++) {
			if(!(ninePukes.get(i).getColor() == 1 || ninePukes.get(i).getColor() == 3)) {
				quan_hei = false;
				break;
			}
		}
		
		// quan_hong
		boolean quan_hong = true;
		for(int i=0;i<ninePukes.size();i++) {
			if(!(ninePukes.get(i).getColor() == 0 || ninePukes.get(i).getColor() == 2)) {
				quan_hong = false;
				break;
			}
		}
		
		// san_shun_zi
		boolean san_shun_zi = (first.getTypeInt() == Constance.tuo_la_ji || first.getTypeInt() == Constance.shun_qing)
				&& (second.getTypeInt() == Constance.tuo_la_ji || second.getTypeInt() == Constance.shun_qing) 
				&& (third.getTypeInt() == Constance.tuo_la_ji || third.getTypeInt() == Constance.shun_qing);
		
		// shuang_tong_hua_shun
		boolean shuang_tong_hua_shun = second.getTypeInt() == Constance.shun_qing && third.getTypeInt() == Constance.shun_qing;
		
		// shuang_san_tiao
		boolean shuang_san_tiao = second.getTypeInt() == Constance.san_ge_tou && third.getTypeInt() == Constance.san_ge_tou;
		
		// san_qing
		boolean san_qing = first.getTypeInt() >= Constance.jin_hua;
		
		if(quan_hei) {
			res.add(Constance.xi_quan_hei);
		}
		
		if(quan_hong) {
			res.add(Constance.xi_quan_hong);
		}
		
		if(san_shun_zi) {
			res.add(Constance.xi_san_shun_zi);
		}
		
		if(shuang_tong_hua_shun) {
			res.add(Constance.xi_shuang_tong_hua_shun);
		}
		
		if(shuang_san_tiao) {
			res.add(Constance.xi_shuang_san_tiao);
		}
		
		if(san_qing) {
			res.add(Constance.xi_san_qing);
		}
		
		return res;
	}


	private static List<Integer> bigSurp(List<ThreePuke> pukes){
		
		List<Integer> res = new ArrayList<Integer>();
		
		ThreePuke first = pukes.get(0);
		ThreePuke second = pukes.get(1);
		ThreePuke third = pukes.get(2);
		
		List<Poker> ninePukes = new ArrayList<Poker>();
		
		for(int i=0;i<pukes.size();i++) {
			ninePukes.addAll(pukes.get(i).getPukes());
		}
		
		reorder(ninePukes);
		
		// xi_san_tong_hua_shun xi_jiu_lian_tong_hua_shun
		
		if(first.getTypeInt() == Constance.shun_qing && second.getTypeInt() == Constance.shun_qing && third.getTypeInt() == Constance.shun_qing) {
			// 同一花色
			if(first.getPukes().get(0).getColor() == second.getPukes().get(0).getColor() && first.getPukes().get(0).getColor() == third.getPukes().get(0).getColor()) {
				//是不是 A23
				if(first.getPukes().get(2).getIndex() == 12) {
					if(second.getPukes().get(0).getIndex() == 4 && third.getPukes().get(0).getIndex() == 7) {
						res.add(Constance.xi_jiu_lian_tong_hua_shun);
						return res;
					}
				}

				if(second.getPukes().get(0).getIndex() - first.getPukes().get(0).getIndex() == 3 && third.getPukes().get(0).getIndex() - second.getPukes().get(0).getIndex() == 3) {
					res.add(Constance.xi_jiu_lian_tong_hua_shun);
					return res;
				}
			}
			
			res.add(Constance.xi_san_tong_hua_shun);
			return res;
		}
		
		// xi_quan_san_tiao
		
		if(first.getTypeInt() == Constance.san_ge_tou && second.getTypeInt() == Constance.san_ge_tou && third.getTypeInt() == Constance.san_ge_tou) {
			res.add(Constance.xi_quan_san_tiao);
		}
		
		// xi_jiu_lian_shun
		
		if(first.getTypeInt() == Constance.tuo_la_ji && second.getTypeInt() == Constance.tuo_la_ji && third.getTypeInt() == Constance.tuo_la_ji) {
			
			//是不是 A23
			if(first.getPukes().get(2).getIndex() == 12) {
				if(second.getPukes().get(0).getIndex() == 4 && third.getPukes().get(0).getIndex() == 7) {
					res.add(Constance.xi_jiu_lian_shun);
					return res;
				}
			}

			if(second.getPukes().get(0).getIndex() - first.getPukes().get(0).getIndex() == 3 && third.getPukes().get(0).getIndex() - second.getPukes().get(0).getIndex() == 3) {
				res.add(Constance.xi_jiu_lian_shun);
				return res;
			}
		}
		
		// xi_shuang_zha_dan xi_zha_dan
		
		int[] A = new int[13];
		int zhadan = 0;
		
		for(int i=0;i<ninePukes.size();i++) {
			A[ninePukes.get(i).getIndex()] ++;
		}
		
		for(int i=0;i<A.length;i++) {
			if(A[i] == 4)zhadan ++;
		}
		
		if((zhadan == 1 && third.getTypeInt() == Constance.san_ge_tou) || (zhadan == 2 && third.getTypeInt() == Constance.san_ge_tou && second.getTypeInt() != Constance.san_ge_tou)) {
			res.add(Constance.xi_zha_dan);
			return res;
		}
		
		if(zhadan == 2 && second.getTypeInt() == Constance.san_ge_tou) {
			res.add(Constance.xi_shuang_zha_dan);
			return res;
		}
		
		return res;
	}
	
	
	private static List<Poker> reorder(List<Poker>p) {
        Collections.sort(p,new Comparator<Poker>(){  
            public int compare(Poker arg0, Poker arg1) {  
                int id0 = arg0.getId();
                int id1 = arg0.getId();
                if(id0 > id1) return 1;
                if(id0 < id1) return -1;
                else return 0;
            }  
        });
		return p;
	}
}
