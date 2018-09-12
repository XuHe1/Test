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
            Thread.sleep(10000l);
            dos.writeUTF("hello client");
            dos.writeUTF("I am server"); // 写并不阻塞
            System.out.println("do next thing");
            dos.flush();
            dos.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
