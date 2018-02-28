package com.gy.biji.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gy.biji.constance.Constance;
import com.gy.biji.entity.BiFenMes;
import com.gy.biji.entity.TotalMes;
import com.gy.biji.entity.XiPaiFenMes;

public class BiFen {
	
	public static List<TotalMes> bifen(List<TotalMes>totalMes) {
		List<TotalMes>aban = new ArrayList<TotalMes>();
		
		int length = totalMes.size();
		
		int total_people = totalMes.size();
		
		for(int i=0;i<totalMes.size();i++)
			if(totalMes.get(i).getPukes().giveup)
				length--;
		
		if(length == 0) {
			return totalMes;
		}
		
		for(int i=0;i<totalMes.size();i++) {
			if(totalMes.get(i).getPukes().giveup) {
				totalMes.get(i).getBiFenDetail().add(new BiFenMes(-1*(total_people-1),false,"弃牌"));
				totalMes.get(i).getBiFenDetail().add(new BiFenMes(-1*(total_people-1),false,"弃牌"));
				totalMes.get(i).getBiFenDetail().add(new BiFenMes(-1*(total_people-1),false,"弃牌"));
				
				totalMes.get(i).setTotal(totalMes.get(i).getTotal() + -3*(total_people-1));
				totalMes.get(i).setTotal_left(totalMes.get(i).getTotal_left() + -3*(total_people-1));
				
				aban.add(totalMes.get(i));
				totalMes.remove(i);
				i--;
			}
		}
		
		int sum = length*(length-1)/2 + (total_people - length)*(total_people-1);

		for(int k=0;k<3;k++) {
			
		reorderTotalMes(totalMes,k);
		
			int index = length-1;
			for(int i=totalMes.size()-1;i>=0;i--) {
				
				TotalMes tm = totalMes.get(totalMes.size()-1-i);
				if(tm.getPukes().isGiveup()) continue;
				
				if(i == 0) {
					BiFenMes newBf = new BiFenMes(sum,true,"赢了 "+sum+" 分");
					tm.getBiFenDetail().add(newBf);
					tm.setTotal(tm.getTotal() + sum);
					tm.setTotal_left(tm.getTotal_left() + sum);
					continue;
				}
				BiFenMes newBf = new BiFenMes(-1*index,false,"输了 "+index+" 分"); 
				tm.getBiFenDetail().add(newBf);
				tm.setTotal(tm.getTotal() + -1*index);
				tm.setTotal_left(tm.getTotal_left() + -1*index);
				index--;
			}
		
		}
		
		// 喜牌
		for(int i=0;i<totalMes.size();i++) {
			TotalMes tm = totalMes.get(i);
			if(tm.getPukes().isGiveup()) continue;
			
			List<Integer> il = Surprise.judgeSurp(tm.getPukes());
			if(il.size() == 0) continue;
			
			for(int j=0;j<il.size();j++) {
				int xi_type = il.get(j);
				for(int k=0;k<totalMes.size();k++) {
					
					TotalMes tmK = totalMes.get(k);
					if(tmK.getPukes().isGiveup()) continue;
					if(k == i) {
						// 可累加喜牌
						int point = Constance.xi_pai_fen[xi_type] * (length-1);
						if(xi_type <= 5) {
							point = (total_people-1)*(length-1);
						}
						XiPaiFenMes xpf = new XiPaiFenMes(point,true,Constance.xi_pai[xi_type]);
						tmK.getXiPaiFenDetail().add(xpf);
						tmK.setTotal(tmK.getTotal() + point);
						tmK.setTotal_left(tmK.getTotal_left() + point);
						continue;
					}
					int point = Constance.xi_pai_fen[xi_type] * (-1);
					if(xi_type <= 5) {
						point = (total_people-1)*(-1);
					}
					XiPaiFenMes xpf = new XiPaiFenMes(point,false,"");
					tmK.getXiPaiFenDetail().add(xpf);
					tmK.setTotal(tmK.getTotal() + point);
					tmK.setTotal_left(tmK.getTotal_left() + point);
				}
			}
		}
		
		for(int i=0;i<totalMes.size();i++) {

			TotalMes tm = totalMes.get(i);
			if(tm.getPukes().isGiveup()) continue;
			
			boolean b = true;
			
			for(int j=0;j<tm.getBiFenDetail().size();j++) {
				if(!tm.getBiFenDetail().get(j).isWin()) {
					b = false;
					break;
				}
			}
			//没通关
			if(!b) continue;
			
			//通关
			for(int k=0;k<totalMes.size();k++) {
				
				TotalMes tmK = totalMes.get(k);
				if(tmK.getPukes().isGiveup()) continue;
				if(k == i) {
					// 可累加喜牌
					int point = (total_people-1)*(length-1);
					XiPaiFenMes xpf = new XiPaiFenMes(point,true,Constance.tong_guan);
					tmK.getXiPaiFenDetail().add(xpf);
					tmK.setTotal(tmK.getTotal() + point);
					tmK.setTotal_left(tmK.getTotal_left() + point);
					continue;
				}
				
				int point = (total_people-1)*(-1);
				XiPaiFenMes xpf = new XiPaiFenMes(point,false,Constance.tong_guan);
				tmK.getXiPaiFenDetail().add(xpf);
				tmK.setTotal(tmK.getTotal() + point);
				tmK.setTotal_left(tmK.getTotal_left() + point);
			}
		}
		
		totalMes.addAll(aban);
		return totalMes;
	}

	private static void reorderTotalMes(List<TotalMes> totalMes, final int first) {
		Collections.sort(totalMes,new Comparator<TotalMes>(){  
            public int compare(TotalMes arg0, TotalMes arg1) {  
                return Compare.compare(arg0.getPukes().getTpukes().get(first), arg1.getPukes().getTpukes().get(first));
            }  
        });
	}
	
}
