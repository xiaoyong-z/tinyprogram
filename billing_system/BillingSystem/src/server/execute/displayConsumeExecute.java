package server.execute;

import model.cardTable;
import model.consumeTable;
import server.database.dao.consumeDao;
import server.database.impl.consumeDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class displayConsumeExecute {
    Socket socket;
    public displayConsumeExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            String cardNumber = (String)objectInputStream.readObject();
            consumeDao consumedao = new consumeDaoImpl();
            Object[][] listable = consumedao.listTable(cardNumber);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(new consumeTable(listable));

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
