package client.service.util;

import java.io.*;
import java.net.Socket;

public class userUtil {
    public static void close(DataInputStream dataInputStream, DataOutputStream dataOutputStream,
                             ObjectInputStream objectOutputStream, ObjectOutputStream objectInputStream,
                             Socket socket){
        try {
            dataInputStream.close();
            dataOutputStream.close();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(DataInputStream dataInputStream, DataOutputStream dataOutputStream,
                             ObjectInputStream objectOutputStream,
                             Socket socket){
        try {
            dataInputStream.close();
            dataOutputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
