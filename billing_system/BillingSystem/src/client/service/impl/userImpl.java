package client.service.impl;

import client.service.util.userUtil;
import model.user;
import client.service.dao.userDao;
import model.userTable;


import java.io.*;
import java.net.Socket;

public class userImpl implements userDao {
    private byte[] bytes;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    @Override
    public int signin(String userName, String passWord){
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("登录".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);


            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(userName);
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            user my_user = (user)objectInputStream.readObject();
            if(my_user != null && my_user.getPassword().equals(passWord)){
                result = 1;
            }

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream, objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int insert(String userName, String passWord) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("添加用户".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(userName);
            objectOutputStream.writeObject(passWord);
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            result = (int)objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream, objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int update(int id, String passWord) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("更新用户".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(id);
            objectOutputStream.writeObject(passWord);
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            result = (int)objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream, objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("删除用户".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(id);
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            result = (int)objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream, objectOutputStream, socket);
        }
        return result;
    }

    @Override
    public Object[][] listTable() {
        Object[][] table = null;
        try {
            bytes = new byte[1000];
            socket = new Socket("localhost", 18888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("显示用户".getBytes());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(bytes);

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            userTable usertable = (userTable) objectInputStream.readObject();
            table = usertable.getUserTable();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            userUtil.close(dataInputStream, dataOutputStream,
                    objectInputStream,  socket);
        }
        return table;
    }
}
