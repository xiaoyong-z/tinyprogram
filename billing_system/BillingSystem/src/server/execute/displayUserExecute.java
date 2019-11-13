package server.execute;

import model.user;
import model.userTable;
import server.database.dao.adminDao;
import server.database.impl.adminDaoImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class displayUserExecute {
    Socket socket;
    public displayUserExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            adminDao admindao = new adminDaoImpl();
            Object[][] listable = admindao.listTable();
            userTable usertable= new userTable(listable);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(usertable);

            objectOutputStream.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
