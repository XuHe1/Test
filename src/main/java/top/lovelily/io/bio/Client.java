package top.lovelily.io.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
                 socket = new Socket("127.0.0.1", 8888);
                //socket = new Socket();
                //socket.setReceiveBufferSize(120000); // must be set before connect(bind), TCP window size
                //socket.connect(new InetSocketAddress("47.92.55.13", 8888));
                //socket.connect(new InetSocketAddress(8888));

                System.out.println("Current Recv Buffer Size:" + socket.getReceiveBufferSize()); // Current Buffer Size:131768
                System.out.println("Current Send Buffer Size:" + socket.getSendBufferSize()); // Current Buffer Size:131768
                System.out.println(socket.getKeepAlive()); // default false
//                InputStream inputStream = socket.getInputStream(); // InputStream一次性的， 这里调用的话，下面调用无效
//                System.out.println(inputStream.read());

//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeUTF("hello server");
//
//                dos.flush();
//                dos.close();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            System.out.println(dis.readUTF()); //阻塞，等待有数据写入
            //Thread.sleep(10000l);
                int pre = -1;
            while (true){
                String s = dis.readUTF(); //阻塞，等待有数据写入
                System.out.println(s);
//                int cur = Integer.valueOf(s);
//              //  System.out.println(cur);
//                if (cur < pre) {
//                    System.out.println("OUT OF ORDER!"); // 同一个连接不会乱序
//                }
//                pre = cur;

            }
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
