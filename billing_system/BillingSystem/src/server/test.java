package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class test {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 18888);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream.write("12345".getBytes());
        socket.close();
    }
}
