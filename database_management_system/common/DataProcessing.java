package common;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class DataProcessing {

	private static boolean connectToDB=false;
	private static String url ="jdbc:mysql://localhost:3306/document?characterEncoding=utf8&useSSL=true"; //数据库连接字符串 
	private static String driverName="com.mysql.jdbc.Driver";                  // 加载数据库驱动类
	private static String user="root";                                      // 数据库用户
	private static String dbpassword="123456"; 
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
	
	static Hashtable<String, User> users;
	static Hashtable<String, Doc> docs;

	static {
		users = new Hashtable<String, User>();
		Init();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		docs = new Hashtable<String,Doc>();

	}
	
	
	public static  void Init(){
		// connect to database
        try{ 	//定义一个表头为column的空表模型
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, dbpassword);
            stmt = conn.createStatement(); 
        }
        catch(Exception e){ 
        	JOptionPane.showMessageDialog(null, "数据库加载失败!");
        }
	}
	
	public static String search_filename(String sql) {
		String filename = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				filename = rs.getString("filename");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "文件未找到");
		}
		return filename;
	}
	
	public static ResultSet executeDB(String sql) {
		try {
			rs = stmt.executeQuery(sql);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "数据库执行错误");
		}
		return rs;
	}
        
    public static void close() {
    	try {
			rs.close(); 
		    stmt.close();  
		    conn.close();
    	} catch(Exception e){
    		
    	}
	}

	public static Doc searchDoc(String ID) throws SQLException {
		if (docs.containsKey(ID)) {
			Doc temp =docs.get(ID);
			return temp;
		}
		return null;
	}

	public static Enumeration<Doc> getAllDocs() throws SQLException{
		Enumeration<Doc> e  = docs.elements();
		return e;
	}
	
	public static boolean insertDocDB(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
		String sql = "insert into doc_info values('" + ID +"','" + creator + "','" + timestamp + "','" + description + "','" + filename + "')";
		//"insert into user_info values('"+name+"','"+password+"','"+role+"')";
		//insert into doc_info values('1','kate','2018-12-11 10:38:09.756','1','bookmarks.html')
		try {
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			//e.getMessage();
		}
		return false;
		
	}
	
	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
		Doc doc;
		if (docs.containsKey(ID))
			return false;
		else{
			doc = new Doc(ID,creator,timestamp,description,filename);
			docs.put(ID, doc);
			return true;
		}
	}

	public static User searchUser(String name) throws SQLException{
//		if ( !connectToDB )
//			throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		if (users.containsKey(name)) {
			return users.get(name);
		}
		return null;
	}
	
	public static User searchUserDB(String name, String password) throws SQLException{
//		if ( !connectToDB )
//			throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );
		String sql = "select * from user_info where username = '" + name + "'";
		User user;
		try {
			ResultSet rs = executeDB(sql);
			while(rs.next()) {
				String init_password = rs.getString("password");
				String role = rs.getString("role");
				if (role.equalsIgnoreCase("administrator"))
					user = new Administrator(name,password, role);
				else if (role.equalsIgnoreCase("operator"))
					user = new Operator(name,password, role);
				else
					user = new Browser(name,password, role);
				if(password.equals(init_password))
					return user;
				return null;
			}
		}catch (Exception e) {	
		}
		return null;
	}

	public static User searchUser(String name, String password) throws SQLException {
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		if (users.containsKey(name)) {
			User temp =users.get(name);
			if ((temp.getPassword()).equals(password))
				return temp;
		}
		return null;
	}

	public static Enumeration<User> getAllUser() throws SQLException{
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		String sql = "SELECT * FROM user_info";
	    ResultSet rs = stmt.executeQuery(sql);
		users = new Hashtable<String, User>();
		while(rs.next()) {
			String name = rs.getString("username");
			String password = rs.getString("password");
			String role = rs.getString("role");
			users.put(name, new Operator(name,password,role));
		}
		Enumeration<User> e  = users.elements();
		return e;
	}


	public static boolean updateUserDB(String name, String password) throws SQLException{
		try {
			String sql = "update user_info set password='" + password + "' where username='" + name + "'";
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean updateUserDB2(String name, String password, String role) throws SQLException{
		try {
			String sql = "update user_info set password='" + password + "', role='"
					+ role + "' where username='" + name + "'";
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean updateUser(String name, String password, String role) throws SQLException{
		User user;
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Update" );

		if (users.containsKey(name)) {
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name,password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name,password, role);
			else
				user = new Browser(name,password, role);
			users.put(name, user);
			return true;
		}else
			return false;
	}

	public static boolean insertUser(String name, String password, String role) throws SQLException{
		User user;

//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Insert" );

		if (users.containsKey(name))
			return false;
		else{
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name,password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name,password, role);
			else
				user = new Browser(name,password, role);
			users.put(name, user);
			return true;
		}
	}

	public static boolean contain_UserDB(String name) throws SQLException {
		String sql = "select * from user_info where username = '" + name + "'";
		ResultSet rs = executeDB(sql);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	public static boolean insertUserDB(String name, String password, String role){
		try {
			if(contain_UserDB(name)) {
				return false;
			}
			else {
				//"INSERT INTO users (name, role, password) VALUES ('"+name+"', '"+role+"', '"+password+"')"
				String sql = "insert into user_info values('"+name+"','"+password+"','"+role+"')"; 
				stmt.executeUpdate(sql);
				return true;
			}
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean deleteUser(String name) throws SQLException{
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Delete" );

		if (users.containsKey(name)){
			users.remove(name);
			return true;
		}else
			return false;

	}
	
	public static boolean deleteUserDB(String name){
		try {
			if(!contain_UserDB(name)) {
				return false;
			}
			else {
				String sql = "delete from user_info where username='" + name + "'"; 
				stmt.executeUpdate(sql);
				return true;
			}
		}catch(Exception e) {
			return false;
		}
	}

	public static void disconnectFromDB() {
		if ( connectToDB ){
			// close Statement and Connection
			try{
//				if (Math.random()>0.5)
//					throw new SQLException( "Error in disconnecting DB" );
//			}catch ( SQLException sqlException ){
//			    sqlException.printStackTrace();
			}finally{
				connectToDB = false;
			}
		}
	}


	public static void main(String[] args) {

	}

}
