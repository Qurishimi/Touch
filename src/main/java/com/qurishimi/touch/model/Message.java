package com.qurishimi.touch.model;

public class Message {
	
	private String name;

	public Message() {
	
	}
	public Message(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Message [name=" + name + "]";
	}
	
	

}
