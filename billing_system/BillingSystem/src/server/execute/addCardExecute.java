package server.execute;

import model.card;
import model.cardTable;
import model.user;
import server.database.dao.adminDao;
import server.database.dao.cardDao;
import server.database.impl.adminDaoImpl;
import server.database.impl.cardDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class addCardExecute {
    Socket socket;
    public addCardExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            card new_card = (card) objectInputStream.readObject();
            cardDao carddao = new cardDaoImpl();
            int result = carddao.insert(new_card);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(result);

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
