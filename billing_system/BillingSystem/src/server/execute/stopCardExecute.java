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
import java.util.Calendar;

public class stopCardExecute {
    Socket socket;
    public stopCardExecute(Socket socket){
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
            }else if(searchCard.getStatus() != 1){
                result = 2;
            }else{
                Calendar start = consumedao.search(cardNumber);
                Calendar end = Calendar.getInstance();
                int seconds = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000);
                double cost = seconds * 0.002 * 100 / 100;
                double balance = searchCard.getBalance();
                balance = balance - cost;
                result = carddao.stopUpdate(cardNumber, balance, end, cost, start);
                if(balance > 0)
                    consumedao.stop(cardNumber, start);
            }
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
