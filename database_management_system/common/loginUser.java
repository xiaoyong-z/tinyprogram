package common;

public class loginUser {
	private static String name;
	private static String password;
	private static String role;
	
	public static void setname(String new_name){
		name = new_name;
	}
	
	public static void setrole(String new_role){
		role = new_role;
	}
	
	public static void setpassword(String new_pass){
		password = new_pass;
	}
	
	public static String getname() {
		return name;
	}
	
	public static String getrole() {
		return role;
	}
	
	public static String getpassword() {
		return password;
	}
}
