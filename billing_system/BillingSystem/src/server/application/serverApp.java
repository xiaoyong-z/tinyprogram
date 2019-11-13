package server.application;

import server.listener.superListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverApp {
    public static void main(String[] args) throws IOException {
        superListener sl = new superListener();
        sl.startListen();
    }
}
