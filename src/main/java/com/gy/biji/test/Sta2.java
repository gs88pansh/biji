package com.gy.biji.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.gy.biji.constance.Constance;
import com.gy.biji.entity.Poker;
import com.gy.biji.util.Compare;
import com.gy.biji.util.DataGen;


/**
 * 种类能拿钱的概率
 * @author Guier
 *
 */
public class Sta2 {
    public static void main(String[]args) throws Exception {
    	DecimalFormat df = new DecimalFormat("0.000");
    	
    	List<Poker>pl = DataGen.generate();
    	
    	List<Poker>newpl = new ArrayList<Poker>();
    	
    	for(int K=3;K<=9;K++) {
	    	System.out.println(K+"个人的统计情况");
	    	
	    	int total = 10;
	    	
	    	for(int M=0;M<total;M++) {
	    		
		    	Random r = new Random();
		    	
		    	for(int i=52;i>0;i--) {
		    		int m = r.nextInt(i);
		    		newpl.add(pl.get(m));
		    		pl.remove(m);
		    	}
		    	
		    	// 随机之后的 pukelist
		    	pl = newpl;
		    	List<List<Poker>>pll = new ArrayList<List<Poker>>();
		    	int group = 0;
		    	List<Poker>newP = new ArrayList<Poker>();
		    	for(int i=0;i<K*3;i++) {
		    		group ++;
		    		newP.add(pl.get(i));
		    		if(group == 3) {
		    			newP = reorder(newP);
		    			pll.add(newP);
		    			//getType(newP);
		    			
		    			newP = new ArrayList<Poker>();
		    			group = 0;
		    		}
		    	}
		    	

		    	reorderLL(pll);
		    	
		    	//System.out.println(pll);
		    	
		    	getType(pll.get(pll.size()-1));
		    	
	    	}
	    	
	    	
	    	String str = "";
	    	
	    	str += Constance.typeStr[5]+":"+ df.format((double)Constance.san_ge_tou_num*(double)100 / (double)(total)) + "%\n";
	    	str += Constance.typeStr[4]+":"+ df.format((double)Constance.shun_qing_num*(double)100 / (double)(total)) + "%\n";
	    	str += Constance.typeStr[3]+":"+ df.format((double)Constance.jin_hua_num*(double)100 / (double)(total)) + "%\n";
	    	str += Constance.typeStr[2]+":"+ df.format((double)Constance.tuo_la_ji_num*(double)100 / (double)(total)) + "%\n";
	    	str += Constance.typeStr[1]+":"+ df.format((double)Constance.dui_zi_num*(double)100 / (double)(total)) + "%\n";
	    	str += Constance.typeStr[0]+":"+ df.format((double)Constance.dan_zhi_zi_num*(double)100 / (double)(total)) + "%\n";
	    	
	    	System.out.println(str);
	    	
	    	Constance.san_ge_tou_num = 0;
	    	Constance.shun_qing_num = 0;
	    	Constance.jin_hua_num = 0;
	    	Constance.tuo_la_ji_num = 0;
	    	Constance.dui_zi_num = 0;
	    	Constance.dan_zhi_zi_num = 0;
    	}
    	
    }
	public static List<List<Poker>> reorderLL(List<List<Poker>>p) {
        Collections.sort(p,new Comparator<List<Poker>>(){  
            public int compare(List<Poker> p1, List<Poker> p2) {  
            	Integer i = Compare.compare(p1, p2);
            	if(i == null)return 0;
            	return i;
            }  
        });
		return p;
	}
    
	public static List<Poker> reorder(List<Poker>p) {
        Collections.sort(p,new Comparator<Poker>(){  
            public int compare(Poker arg0, Poker arg1) {  
                Integer id0 = arg0.getId();
                Integer id1 = arg1.getId();
                return id0.compareTo(id1);
            }  
        });
		return p;
	}
	
	public static int getType(List<Poker>p) {
		//i0<=i1<=i2
		
		int i0 = p.get(0).getIndex();
		int i1 = p.get(1).getIndex();
		int i2 = p.get(2).getIndex();
		
		int c0 = p.get(0).getColor();
		int c1 = p.get(1).getColor();
		int c2 = p.get(2).getColor();
		
		// 三个头
		if(i0 == i1 && i0 == i2) {
			Constance.san_ge_tou_num++;
			return Constance.san_ge_tou;
		}
		
		// 顺青
		if(((i0+1 == i1 && i1+1 == i2) || (i0 == 0 && i1 == 1 && i2 == 12)) && (c0 == c1 && c0 == c2)) {
			Constance.shun_qing_num++;
			return Constance.shun_qing;
		}
	
		// 金花
		if(c0 == c1 && c0 == c2) {
			Constance.jin_hua_num++;
			return Constance.jin_hua;
		}
		
		// 拖拉机
		if(i0+1 == i1 && i1+1 == i2) {
			Constance.tuo_la_ji_num++;
			return Constance.tuo_la_ji;
		}
		
		// 对子 
		if(i0 == i1 || i0 == i2 || i1 == i2) {
			Constance.dui_zi_num++;
			return Constance.dui_zi;
		}
		Constance.dan_zhi_zi_num++;
		return Constance.dan_zhi_zi;
		
	}
	
}
