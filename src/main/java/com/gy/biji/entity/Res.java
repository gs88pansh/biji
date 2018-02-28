package com.gy.biji.entity;

public class Res<T> {
	public T data;
	public String type;
	public String message;
	public String status;
	
	
	public Res(String type,String message,String status,T data) {
		this.type = type;
		this.message = message;
		this.status = status;
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
