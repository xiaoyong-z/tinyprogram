package client.service.impl;

import client.service.dao.consumeDao;
import client.service.util.userUtil;
import model.cardTable;
import model.consumeTable;

import java.io.*;
import java.net.Socket;

public class consumImpl implements consumeDao{
    private byte[] bytes;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    @Override
    public Object[][] listTable(String cardNumber) {
        Object[][] table = null;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("查询消费".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            consumeTable consumetable = (consumeTable) objectInputStream.readObject();
            table = consumetable.getCardTable();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  socket);
        }
        return table;
    }
}
