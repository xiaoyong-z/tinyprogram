package server.execute;

import model.user;
import server.database.dao.adminDao;
import server.database.impl.adminDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class updateUserExecute {
    Socket socket;
    public updateUserExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            int id = (int) objectInputStream.readObject();
            String password = (String) objectInputStream.readObject();
            adminDao admindao = new adminDaoImpl();
            int result = admindao.update(id, password);
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
