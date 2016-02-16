package com.java;

import java.util.Set;

public class MusicType {
	private int id;
	private String typeName;
	private Set<User> users;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "MusicType [id=" + id + ", typeName=" + typeName + ", users=" + users + "]";
	}
	
	

}
