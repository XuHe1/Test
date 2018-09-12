package com.etianxia.io.bio;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Desc: Client
 * Author: xuhe
 * Date: 2018/9/11 上午10:42
 * Version: 1.0
 */
public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8888);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            //System.out.println(dis.readUTF()); //阻塞，等待有数据写入
            // System.out.println(dis.readUTF()); //阻塞，等待有数据写入
            System.out.println("do next thing");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
