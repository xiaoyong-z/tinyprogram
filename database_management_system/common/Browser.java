package common;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import frame.mainFrame;


public class Browser extends User {

	Browser(String name,String password,String role){
		super(name,password,role);
	}
	
	
    public void showMenu() {
    	mainFrame window = new mainFrame();
		window.setVisible(true);
		window.bropermission();
    }
}
