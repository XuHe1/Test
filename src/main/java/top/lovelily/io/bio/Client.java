package top.lovelily.io.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Desc: Client
 * Author: xuhe
 * Date: 2018/9/11 上午10:42
 * Version: 1.0
 */
public class Client {
    public static void main(String[] args) {
        int num = 5000;
        for (int i = 0; i < num; i++) {
            Socket socket = null;
            try {
                socket = new Socket("localhost", 8888);
                InputStream inputStream = socket.getInputStream();
                System.out.println(inputStream.read());

//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeUTF("hello server");
//
//                dos.flush();
//                dos.close();

//            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            System.out.println(dis.readUTF()); //阻塞，等待有数据写入
//            Thread.sleep(10000l);
//            for (int i = 0; i < 10; i ++) {
//                System.out.println(dis.readUTF()); //阻塞，等待有数据写入
//            }
//            System.out.println("do next thing");
            } catch (IOException e) {
//                e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

            }
        }

    }
}
