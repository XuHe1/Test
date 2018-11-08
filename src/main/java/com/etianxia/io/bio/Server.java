package com.etianxia.io.bio;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Desc: Server
 * Author: xuhe
 * Date: 2018/9/11 上午10:46
 * Version: 1.0
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept(); // 阻塞，等待client连接
            System.out.println("a client connected");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Thread.sleep(10000l); // 阻塞client
            dos.writeUTF("hello client");
            for (int i = 0; i < 10; i ++){
                dos.writeUTF("I am server"); // 异步写，写不阻塞，调用native方法，由操作系统完成真正的写操作，这时已经和socket没有关系了，即使socket已经close
            }

            System.out.println("do next thing");
            dos.flush();
            dos.close();
            socket.close();
            serverSocket.close();
            System.out.println(socket.isClosed());
            System.out.println(serverSocket.isClosed());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
