package com.gy.biji.config;


import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.config.annotation.EnableWebSocket;  
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;  
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.gy.biji.handler.SystemWebSocketHandler;
import com.gy.biji.interceptor.WebSocketHandshakeInterceptor;  
 


@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements  WebSocketConfigurer{  

  @Override  
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
      registry.addHandler(myHandler(), "/webSocketServer").addInterceptors(myInterceptor());  
  }  

  @Bean  
  public WebSocketHandler myHandler() {
      return new SystemWebSocketHandler();  
  }
  
  @Bean
  public HandshakeInterceptor myInterceptor() {
      return new WebSocketHandshakeInterceptor();  
  }
 } 