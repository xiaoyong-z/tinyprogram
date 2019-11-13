package server.execute;

import model.card;
import server.database.dao.cardDao;
import server.database.dao.consumeDao;
import server.database.impl.cardDaoImpl;
import server.database.impl.consumeDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class drawBackCardExecute {
    Socket socket;
    public drawBackCardExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            String cardNumber = (String) objectInputStream.readObject();
            cardDao carddao = new cardDaoImpl();
            card searchCard = carddao.search(cardNumber);
            int result;
            if(searchCard == null){
                result = 0;
            }else if(searchCard.getBalance() < 0){
                result = 3;
            }else if(searchCard.getStatus() != 0){
                result = 2;
            }else{
                result = carddao.drawBack(cardNumber);
            }
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
