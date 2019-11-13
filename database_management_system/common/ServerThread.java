package common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Enumeration;

import common.DataProcessing;
import common.Doc;
import common.NetTransfer;
import common.User;

public class ServerThread extends Thread {
	Socket socket = null;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
		InputStream is=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		ObjectInputStream ois=null;
		ObjectOutputStream oos=null;
		FileInputStream fis=null;
		DataOutputStream dos=null;
		FileOutputStream fos=null;
		DataInputStream dis=null;
		try {
		    
			is = socket.getInputStream();  // 字节输入流
			ois = new ObjectInputStream(is);
			NetTransfer nt =  (NetTransfer) ois.readObject();
			switch (nt.action){
			
				case "login": 
					String[] arrs =  nt.paras.split("\\|");
					User u = DataProcessing.searchUserDB(arrs[0], arrs[1]);

					if (u!=null)
					{
						nt.objRes = u;
						nt.strErr = "123123";
						nt.ifRun = true;
					}
					else{
						nt.ifRun = false;
						nt.strErr = "123";
					}
					socket.shutdownInput();  // 关闭输入流
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(nt);
					oos.flush();
					break;
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos!=null)
					oos.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
