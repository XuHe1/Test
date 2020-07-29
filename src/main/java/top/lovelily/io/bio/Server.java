package top.lovelily.io.bio;

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
            //Thread.sleep(10000l); // 阻塞client
            dos.writeUTF("hello client");
            for (int i = 0; i < 10; i++) {
                StringBuffer sb= new StringBuffer(i);
                sb.append(String.valueOf(i));
                sb.toString();
                dos.writeUTF("I am server " + i); //  并不是真正的写，写的缓存中（发送缓存）， 写调用native方法，由操作系统完成真正的写操作，这时已经和socket没有关系了，即使socket已经close
                System.out.println("do next thing");
                /**
                 *
                 * flush indicates thant previously written have been buffered by the implementation of the output
                 *  stream, such bytes should immediately be written to their intended destination.
                 *
                 *  If the intended destination of this stream is an abstraction provided by
                 *  the underlying operating system, for example a file(fd), then flushing the
                 *  stream guarantees only that bytes previously written to the stream are
                 *  passed to the operating system for writing; it does not guarantee that
                 *  they are actually written to a physical device such as a disk drive.
                 *  TCP 发送队列（内核空间）
                 *
                 */
               // dos.flush(); // flushBuffer, 推送到内核空间:SendQueue(Socket的sendBuffer)
                System.out.println("I am server " + i);
                socket.close();
                Thread.sleep(5000l);
                // 停止Client, 发现还Server端可以继续发一条， 所以确定是写到缓存（send Queue)中的
            }

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
