package server.execute;

import model.user;
import server.database.dao.adminDao;
import server.database.impl.adminDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class addUserExecute {
    Socket socket;
    public addUserExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            String userName = (String) objectInputStream.readObject();
            String passWord = (String) objectInputStream.readObject();
            adminDao admindao = new adminDaoImpl();
            int result = admindao.insert(userName, passWord);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(result);

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
