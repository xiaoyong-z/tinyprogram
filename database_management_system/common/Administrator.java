package common;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import frame.mainFrame;

public class Administrator extends User {
	Administrator(String name,String password,String role){
		super(name,password,role);
	}
	
	public static boolean changeUserInfo(String name, String password, String role) throws SQLException {
		//写用户信息到存储
		return (DataProcessing.updateUser(name, password, role));	
	}
	
	public static boolean changeUserInfoDB(String name, String password) throws SQLException {
		//写用户信息到存储
		return (DataProcessing.updateUserDB(name, password));	
	}
	
	public static boolean changeUserInfoDB2(String name, String password, String role) throws SQLException {
		//写用户信息到存储
		return (DataProcessing.updateUserDB2(name, password, role));	
	}
	
	public static boolean delUser(String name) throws SQLException {
		return (DataProcessing.deleteUser(name));	 
	}
	
	public static boolean delUserDB(String name){
		return (DataProcessing.deleteUserDB(name));	 
	}
	
	public static boolean addUser(String name, String password, String role) throws SQLException {
		return (DataProcessing.insertUser(name, password, role));
	}
	
	public static boolean addUserDB(String name, String password, String role) {
		return (DataProcessing.insertUserDB(name, password, role));
	}
	
	public void listUser() throws SQLException {
		Enumeration<User> e= DataProcessing.getAllUser();
		User user;
		while( e.hasMoreElements() ){
			user=e.nextElement();
			System.out.println("Name:"+user.getName()+"\t Password:" +user.getPassword()+"\t Role:" +user.getRole());
		}
	}
	
    public void showMenu() {
    	mainFrame window = new mainFrame();
		window.setVisible(true);
		window.adopermission();
    }
	
}
