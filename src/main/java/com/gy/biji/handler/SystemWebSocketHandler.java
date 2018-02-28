package com.gy.biji.handler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.socket.CloseStatus;  
import org.springframework.web.socket.TextMessage;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.WebSocketMessage;  
import org.springframework.web.socket.WebSocketSession;

import com.gy.biji.constance.WEB;  
  
public class SystemWebSocketHandler implements WebSocketHandler {  
        
        public static ConcurrentMap<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>(); 
           
        @Override  
        public void afterConnectionEstablished(WebSocketSession session) throws Exception { 
        	
            users.put((String)session.getAttributes().get(WEB.websocket_username),session);
            System.out.println("ConnectionEstablished"+"=>当前在线用户的数量是:"+users.size());
            
        }  
       
        @Override  
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {  
              
            //TextMessage returnMessage = new TextMessage(message.getPayload() + "received at server: ");  
                              
            //sendMessageToUsers(returnMessage);                      
        }  
       
        @Override  
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
            if(session.isOpen()){  
                
                session.close();  
            }  
            users.remove((String)session.getAttributes().get(WEB.websocket_username));  
        }  
       
          
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
        	if(session == null)return;
            users.remove((String)session.getAttributes().get(WEB.websocket_username));  
            System.out.println("ConnectionClosed"+"=>当前在线用户的数量是:"+users.size());  
  
        }  
       
        @Override  
        public boolean supportsPartialMessages() {  
            return false;  
        }  
       
        /** 
         * 给所有在线用户发送消息 
         * @param message 
         * @throws IOException 
         */  
        public  static void  sendMessageToUsers(TextMessage message) throws IOException {
        	
        	System.out.println(message.getPayload());
        	
        	for(String set:users.keySet()) {
        		WebSocketSession session = users.get(set);
        		
        		if(session.isOpen()) {
        			
        			session.sendMessage(message);
        			
        		}
        		
        	}
        }   
}  
