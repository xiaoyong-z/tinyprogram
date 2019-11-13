package client.application;

import client.gui.loginFrame;

public class clientApp {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                new loginFrame().setVisible(true);
            }
        });
    }
}
