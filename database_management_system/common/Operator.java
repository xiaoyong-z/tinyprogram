package common;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import javax.swing.JFrame;

import frame.mainFrame;;

public class Operator extends User {
    Operator(String name, String password, String role) {
        super(name, password, role);
    }
    
    public static boolean uploadFileDB(String name, String sourcefile, String ID, String description) throws SQLException, IOException {
        File file = new File(sourcefile.trim());
        String filename = file.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(uploadpath + filename));

        byte[] buffer = new byte[1024];
        while(infile.read(buffer, 0, 1024) != -1){
            targetfile.write(buffer);
        }

        infile.close();
        targetfile.close();

        String creator= name;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //insert record into database
        if (!DataProcessing.insertDocDB(ID, creator,timestamp,description, filename)){
            file =new File(uploadpath+filename);
            file.delete();
            return false;
        }else {
            return true;
        }
    }
    

    public boolean uploadFile(String sourcefile, String ID, String description) throws SQLException, IOException {
        System.out.println("上传文件中... ...");
        File file = new File(sourcefile.trim());
        String filename = file.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(uploadpath + filename));

        byte[] buffer = new byte[1024];
        while(infile.read(buffer, 0, 1024) != -1){
            targetfile.write(buffer);
        }

        infile.close();
        targetfile.close();

        String creator= this.getName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //insert record into database
        if (!DataProcessing.insertDoc(ID, creator,timestamp,description, filename)){
            //System.out.println("上传文件出现错误");
            file =new File(uploadpath+filename);
            file.delete();
            return false;
        }else {
            System.out.println("上传成功。");
            return true;
        }
    }


    public void showMenu() {
    	mainFrame window = new mainFrame();
		window.setVisible(true);
		window.opepermission();
    }
}
