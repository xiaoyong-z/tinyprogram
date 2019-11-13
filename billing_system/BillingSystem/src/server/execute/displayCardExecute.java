package server.execute;

import model.cardTable;
import model.userTable;
import server.database.dao.cardDao;
import server.database.impl.cardDaoImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class displayCardExecute {
    Socket socket;
    public displayCardExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            cardDao carddao = new cardDaoImpl();
            Object[][] listable = carddao.listTable();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(new cardTable(listable));

            objectOutputStream.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
