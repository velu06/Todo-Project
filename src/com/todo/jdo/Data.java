package com.todo.jdo;

import java.util.ArrayList;

public class Data  {
//
//private ArrayList<String> data;
private String username;
private String password;
public Data()
{
	
}
public Data(String username, String password, ArrayList data){
  
    this.username = username;
    this.password = password;
//    this.data = data;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
/*public ArrayList<String> getData() {
	return data;
}
public void setData(ArrayList<String> data) {
	this.data = data;
}*/
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
}
