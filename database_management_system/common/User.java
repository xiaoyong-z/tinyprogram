package common;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

public abstract class User {
	private String name;
	private String password;
	private String role;
	protected static final String uploadpath = "c:\\oop\\downloadpath\\";
	protected static final String downloadpath = "c:\\oop\\downloadpath\\";

	User(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}
	
	public boolean changeUserInfo(String password) throws SQLException {
		//写用户信息到存储
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			System.out.println("修改成功");
			return true;
		}else
			return false;
	}
	
	public abstract void showMenu();
	public static void downloadFileFromDB(String filename, String savepath) throws IOException, SQLException {
		String sql = "select * from doc_info where filename='"+filename+"'";
		String DBfilename = DataProcessing.search_filename(sql);
		
        byte[] buffer = new byte[1024];
		File tempfile = new File(uploadpath + DBfilename);
		String name = tempfile.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempfile));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(savepath));

        while(infile.read(buffer, 0, 1024) != -1){
            targetfile.write(buffer);
        }

        infile.close();
        targetfile.close();
	}
	
	public static boolean downloadFile(String filename) throws IOException, SQLException {
		Doc doc = DataProcessing.searchDoc(filename);
		if(doc == null) {
            return false;
        }
        byte[] buffer = new byte[1024];
		File tempfile = new File(uploadpath + doc.getFilename());
		String name = tempfile.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempfile));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(downloadpath + name));

        while(infile.read(buffer, 0, 1024) != -1){
            targetfile.write(buffer);
        }

        infile.close();
        targetfile.close();
		return true;
	}
	
	public void showFileList() throws SQLException {
        System.out.println("列表:");
	    Enumeration<Doc> e = DataProcessing.getAllDocs();
        while(e.hasMoreElements()){
            Doc doc = e.nextElement();
            System.out.println("Doc ID:" + doc.getID());
            System.out.println("Creator: " + doc.getCreator());
            System.out.println("Timestamp: " + doc.getTimestamp());
            System.out.println("Description: " + doc.getDescription());
            System.out.println("Finename: " + doc.getFilename());
            System.out.println();
        }
	}
	
	public void exitSystem(){
		System.out.println("系统退出, 谢谢使用 ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

}
