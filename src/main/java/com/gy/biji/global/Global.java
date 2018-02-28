package com.gy.biji.global;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.gy.biji.entity.GlobalMes;

public class Global {

	/**
	 * 房间号 ------> 房间信息
	 */
	public static ConcurrentMap<Integer,GlobalMes> globalMap = new ConcurrentHashMap<Integer,GlobalMes>();
	
}
