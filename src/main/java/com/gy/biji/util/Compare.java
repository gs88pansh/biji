package com.gy.biji.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gy.biji.constance.Constance;
import com.gy.biji.entity.Poker;
import com.gy.biji.entity.ThreePuke;

public class Compare {

	public static Integer compare(ThreePuke t1, ThreePuke t2) {
		List<Poker>p1 = t1.getPukes(),p2 = t2.getPukes();
		int type1 = t1.getTypeInt(),type2 = t2.getTypeInt();
		if(type1 > type2)return 1;
		if(type1 < type2)return -1;
		
		int idMax1 = p1.get(2).getId();
		int idMax2 = p2.get(2).getId();
		
		int index12 = p1.get(2).getIndex();
		int index11 = p1.get(1).getIndex();
		int index10 = p1.get(0).getIndex();
		 
		int index22 = p2.get(2).getIndex();
		int index21 = p2.get(1).getIndex();
		int index20 = p2.get(0).getIndex();
		 
		if(type1 == Constance.san_ge_tou) {
			
			if(p1.get(2).getId() > p2.get(2).getId()) return 1;
			
			if(p1.get(2).getId() == p2.get(2).getId()) return 0;
			
			return -1;
		}
		 
		if(type1 == Constance.shun_qing || type1 == Constance.tuo_la_ji) {
			if(index12 == 12 && index11 == 1 && index22 == 12 && index21 == 1) {
				
				if(idMax1 > idMax2) return 1;
				
				if(idMax1 == idMax2) return 0;
				
				else return -1;
			}
			
			if(index12 == 12 && index11 == 1)
				return -1;
			
			if(index22 == 12 && index21 == 1)
				return 1;
			
			if(idMax1 > idMax2) return 1;
			
			if(idMax1 == idMax2) return 0;
			
			return -1;
		}
		 
		if(type1 == Constance.jin_hua || type1 == Constance.dan_zhi_zi) {
			if(index12 > index22) return 1;
			if(index12 < index22) return -1;
			 
			if(index11 > index21) return 1;
			if(index11 < index21) return -1;
			 
			if(index10 > index20) return 1;
			if(index10 < index20) return -1;
			
			if(idMax1 > idMax2)return 1;
			if(idMax1 < idMax2)return -1;
			return 0;
		}
		 
		if(type1 == Constance.dui_zi) {
			int dui1 = -1,dui2 = -1,dan1 = -1,dan2 = -1;
			
			if(index11 == index12){ dui1 = index11;dan1 = index10; }
			else if(index11 == index10){ dui1 = index11;dan1 = index12; }
			else { dui1 = index10;dan1 = index11; }
			
			if(index21 == index22){ dui2 = index21;dan2 = index20; }
			else if(index21 == index20){ dui2 = index21;dan2 = index22; }
			else { dui2 = index20;dan2 = index21; }
			 
			if(dui1 > dui2) return 1;
			if(dui1 < dui2) return -1;
			
			if(dan1 > dan2) return 1;
			if(dan1 < dan2) return -1;
			
			if(idMax1 > idMax2)return 1;
			if(idMax1 < idMax2)return -1;
			
			return 0;
		}
		return 0;
	}
	
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return 
	 * 		p1 > p2 ===>1
	 * 		p1 < p2 ===>-1
	 * 		p1 = p2 ===>0
	 * 		null wrong
	 */
	public static Integer compare(List<Poker>p1,List<Poker>p2) {
		if(p1.size()!=3 || p2.size()!=3) return null;
		 reorder(p1);
		 reorder(p2);
		 
		 int type1 = getType(p1);
		 
		 int type2 = getType(p2);
		 
		 if(type1 > type2)return 1;
		 
		 if(type1 < type2)return -1;
		 


		 int idMax1 = p1.get(2).getId();
		 int idMax2 = p2.get(2).getId();
		 
		 int index12 = p1.get(2).getIndex();
		 int index11 = p1.get(1).getIndex();
		 int index10 = p1.get(0).getIndex();
		 
		 int index22 = p2.get(2).getIndex();
		 int index21 = p2.get(1).getIndex();
		 int index20 = p2.get(0).getIndex();
		 
		 if(type1 == Constance.san_ge_tou) {
			 if(p1.get(2).getId() > p2.get(2).getId()) return 1;
			 
			 if(p1.get(2).getId() == p2.get(2).getId()) return 0;
			 
			 return -1;
		 }
		 
		 if(type1 == Constance.shun_qing || type1 == Constance.tuo_la_ji) {
			 if(index12 == 12 && index11 == 1 && index22 == 12 && index21 == 1) {
				 if(idMax1 > idMax2) return 1;
				 if(idMax1 == idMax2) return 0;
				 else return -1;
			 }
			 
			 if(index12 == 12 && index11 == 1)
				 return -1;
			 
			 if(index22 == 12 && index21 == 1)
				 return 1;
			 
			 if(idMax1 > idMax2) return 1;
			 
			 if(idMax1 == idMax2) return 0;
			 
			 return -1;
		 }
		 
		 if(type1 == Constance.jin_hua || type1 == Constance.dan_zhi_zi) {
			 if(index12 > index22) return 1;
			 if(index12 < index22) return -1;
			 
			 if(index11 > index21) return 1;
			 if(index11 < index21) return -1;
			 
			 if(index10 > index20) return 1;
			 if(index10 < index20) return -1;
			 
			 if(idMax1 > idMax2)return 1;
			 if(idMax1 < idMax2)return -1;
			 return 0;
		 }
		 
		 if(type1 == Constance.dui_zi) {
			 int dui1 = -1,dui2 = -1,dan1 = -1,dan2 = -1;
			 
			 if(index11 == index12){ dui1 = index11;dan1 = index10; }
			 else if(index11 == index10){ dui1 = index11;dan1 = index12; }
			 else { dui1 = index10;dan1 = index11; }
			 
			 if(index21 == index22){ dui2 = index21;dan2 = index20; }
			 else if(index21 == index20){ dui2 = index21;dan2 = index22; }
			 else { dui2 = index20;dan2 = index21; }
			 
			 if(dui1 > dui2) return 1;
			 if(dui1 < dui2) return -1;
			 
			 if(dan1 > dan2) return 1;
			 if(dan1 < dan2) return -1;
			 
			 if(idMax1 > idMax2)return 1;
			 if(idMax1 < idMax2)return -1;
			 
			 return 0;
		 }

		 return 0;
	}
	
	private static int getType(List<Poker>p) {
		//i0<=i1<=i2
		
		int i0 = p.get(0).getIndex();
		int i1 = p.get(1).getIndex();
		int i2 = p.get(2).getIndex();
		
		int c0 = p.get(0).getColor();
		int c1 = p.get(1).getColor();
		int c2 = p.get(2).getColor();
		
		// ����ͷ
		if(i0 == i1 && i0 == i2) return Constance.san_ge_tou;
		
		// ˳��
		if(((i0+1 == i1 && i1+1 == i2) || (i0 == 0 && i1 == 1 && i2 == 12)) && (c0 == c1 && c0 == c2)) return Constance.shun_qing;
	
		// ��
		if(c0 == c1 && c0 == c2) return Constance.jin_hua;
		
		// ������
		if(i0+1 == i1 && i1+1 == i2) return Constance.tuo_la_ji;
		
		// ���� 
		if(i0 == i1 || i0 == i2 || i1 == i2) return Constance.dui_zi;
		
		return Constance.dan_zhi_zi;
		
	}
	
	public static List<Poker> reorder(List<Poker>p) {
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

	public static List<Poker> reorderDesc(List<Poker>p) {
        Collections.sort(p,new Comparator<Poker>(){  
            public int compare(Poker arg0, Poker arg1) {  
                int id0 = arg0.getId();
                int id1 = arg1.getId();
                if(id0 > id1) return -1;
                if(id0 < id1) return 1;
                else return 0;
            }  
        });
		return p;
	}
	
	public static List<ThreePuke> reorderThreePuke(List<ThreePuke>p) {
        Collections.sort(p,new Comparator<ThreePuke>(){  
            public int compare(ThreePuke p1, ThreePuke p2) {
            	Integer i = Compare.compare(p1, p2);
            	return i;
            }  
        });
		return p;
	}
}