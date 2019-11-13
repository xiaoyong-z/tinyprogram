package common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import common.NetTransfer;
import common.User;

public class Client {
	public NetTransfer Login(String name, String password) {
		NetTransfer tem = new NetTransfer();
		tem.action = "login";
		tem.paras = name + "|" + password;
		return tem;
	}
	
	public Object Start(String[] args) throws Exception {
		
			Socket socket = new Socket("localhost", 8888);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			NetTransfer res = null;
			switch (args[0]) {
			case "login":
				res = Login(args[1], args[2]);
				break;

			default:
				break;
			}
			
			oos.writeObject(res);
			oos.flush();
			socket.shutdownOutput();

			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			NetTransfer nt =  (NetTransfer) ois.readObject();
			
			switch (nt.action) {
			case "login":
				if (nt.ifRun) {
					User temp = (User)nt.objRes;
					temp.showMenu();
					break;
				}else {
					throw new Exception();
				}
				
			default:break;
			}
			socket.shutdownInput(); 
			oos.close();
			
			return null;
			
		
	}
}
