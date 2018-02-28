package com.gy.biji.schedule;

import java.util.concurrent.ConcurrentMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gy.biji.entity.GlobalMes;
import com.gy.biji.global.Global;
@Component("GlobalSchedule")
public class GlobalSchedule {
	@Scheduled(cron="0 0/30 * * * *")
	void destroyOrder(){
		ConcurrentMap<Integer,GlobalMes>map = Global.globalMap;
		for(Integer key : map.keySet()) {
			
			GlobalMes gm = map.get(key);
			if(System.currentTimeMillis() - gm.create_time_stamp > 2 * 3600 * 3600) {
				map.remove(key);
				System.out.println(map.size());
			}
			
		}
	}
}
