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

public class useCardExecute {
    Socket socket;
    public useCardExecute(Socket socket){
        this.socket = socket;
    }

    public void start(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            String cardNumber = (String) objectInputStream.readObject();
            cardDao carddao = new cardDaoImpl();
            card searchCard = carddao.search(cardNumber);
            consumeDao consumedao = new consumeDaoImpl();
            int result;
            if(searchCard == null){
                result = 0;
            }else if(searchCard.getStatus() != 0){
                result = 2;
            }else if(searchCard.getBalance() < 0){
                result = 3;
            }else{
                consumedao.use(searchCard.getCardNumber());
                result = carddao.useUpdate(cardNumber);
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
