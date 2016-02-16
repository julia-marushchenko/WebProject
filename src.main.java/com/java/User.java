package com.java;

import java.util.Set;

public class User {
	
	private int id;
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private int age;
	private Address address;
	private Role role;
	private Set <MusicType> musicTypes;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Set<MusicType> getMusicTypes() {
		return musicTypes;
	}
	public void setMusicTypes(Set<MusicType> musicTypes) {
		this.musicTypes = musicTypes;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", age=" + age + ", address=" + address + ", role=" + role
				+ ", musicTypes=" + musicTypes + "]";
	}
	
	
		
	
		
	
	
	

}
