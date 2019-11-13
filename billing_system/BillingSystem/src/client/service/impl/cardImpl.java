package client.service.impl;

import client.service.dao.cardDao;
import client.service.util.userUtil;
import model.card;
import model.cardTable;

import java.io.*;
import java.net.Socket;

public class cardImpl implements cardDao {
    private byte[] bytes;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    @Override
    public Object[][] listTable() {
        Object[][] table = null;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("显示卡".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            cardTable cardtable = (cardTable) objectInputStream.readObject();
            table = cardtable.getCardTable();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  socket);
        }
         return table;
    }

    @Override
    public int addCard(String userName, String passWord, double balance) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("添加卡".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            card new_card = new card(userName, passWord, balance);
            objectOutputStream.writeObject(new_card);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int use(String cardNumber) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("卡上机".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int stop(String cardNumber) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("卡下机".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int topUp(String cardNumber, double cardtopUpAmount) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("卡充值".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectOutputStream.writeObject(cardtopUpAmount);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int drawBack(String cardNumber) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("卡退费".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int cancel(String cardNumber) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("卡注销".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cardNumber);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (int) objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  objectOutputStream, socket);
        }
        return result;
    }
}
