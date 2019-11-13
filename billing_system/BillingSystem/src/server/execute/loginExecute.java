package server.execute;

import client.service.dao.userDao;
import client.service.impl.userImpl;
import model.user;
import server.database.dao.adminDao;
import server.database.impl.adminDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class loginExecute {
    Socket socket;
    public loginExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            String userName = (String) objectInputStream.readObject();
            adminDao admindao = new adminDaoImpl();
            user myUser = admindao.login(userName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(myUser);

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
