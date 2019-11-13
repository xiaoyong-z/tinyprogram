package server.listener;

import server.execute.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class superListener {
    private ServerSocket serverSocket;
    public void startListen() throws IOException{
        this.serverSocket = new ServerSocket(18888);
        byte[] choice_byte = new byte[1000];

        while(true){
            System.out.println("⌛⌛⌛⌛⌛⌛️");
            Socket socket = this.serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int length = dataInputStream.read(choice_byte);
            dataOutputStream.write("收到".getBytes());
            String choice_string = new String(choice_byte, 0, length);
            System.out.println("执行: " + choice_string);
            execute(socket, choice_string);
        }
    }

    public void execute(Socket socket, String choice_string){
        if(choice_string.equals("登录")){
            new loginExecute(socket).start();
        }else if(choice_string.equals("添加用户")){
            new addUserExecute(socket).start();;
        }else if(choice_string.equals("更新用户")){
            new updateUserExecute(socket).start();
        }else if(choice_string.equals("删除用户")){
            new deleteUserExecute(socket).start();
        }else if(choice_string.equals("显示用户")){
            new displayUserExecute(socket).start();
        }else if(choice_string.equals("显示卡")){
            new displayCardExecute(socket).start();
        }else if(choice_string.equals("添加卡")){
            new addCardExecute(socket).start();
        }else if(choice_string.equals("卡上机")){
            new useCardExecute(socket).start();
        }else if(choice_string.equals("卡下机")){
            new stopCardExecute(socket).start();
        }else if(choice_string.equals("卡充值")){
            new topUpCardExecute(socket).start();
        }else if(choice_string.equals("卡退费")){
            new drawBackCardExecute(socket).start();
        }else if(choice_string.equals("卡注销")){
            new cancelCardExecute(socket).start();
        }else if(choice_string.equals("查询消费")){
            new displayConsumeExecute(socket).start();
        }
    }
}
