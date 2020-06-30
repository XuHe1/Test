package top.lovelily.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static top.lovelily.io.server.SingleThreadedMultiplexServer.handleWrite;

/**
 * Desc: TestCustomNetty
 * Author: xuhe
 * Date: 2020/6/10 4:53 下午
 * Version: 1.0
 */

public class TestCustomNetty {

    private ServerSocketChannel server;
    private int port = 8080;
    private Selector selector1;
    private Selector selector2;
    private Selector selector3;

    private int workerCount = 2;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector1 = Selector.open(); // epoll_create
            selector2 = Selector.open();
            selector3 = Selector.open();

            server.register(selector1, SelectionKey.OP_ACCEPT); //  epoll_ctrl(fd, ADD, EPOLLIN)
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class NioThread extends Thread {
        private Selector selector;
        private int id; // workerId
        static LinkedBlockingQueue<SocketChannel>[] queues;
        static AtomicInteger idx = new AtomicInteger();
        boolean isBoss = false;
        public NioThread(Selector selector) {
            this.selector = selector;
            this.id = idx.getAndIncrement();
            System.out.println("worker " + this.id + "启动");
        }

        public NioThread(Selector selector, int workerCount) {
            this.selector = selector;
            queues = new LinkedBlockingQueue[workerCount];
            for (int i = 0; i < workerCount; i ++) {
                queues[i] = new LinkedBlockingQueue<>();
            }
            System.out.println("Boss 启动");
            isBoss = true;
        }

        public void run() {
            try {
                while (true) {
                    while (selector.select(10) > 0) { //程序会不断拷贝fd到内核， 内核轮询fd集合 epll_wait()
                        Set<SelectionKey> keySet = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove(); // important
                            if (key.isAcceptable()) {
                                handleAccept(key);
                            } else if (key.isReadable()) {
                                handleRead(key);
                            }
//                            else if (key.isWritable()) { // 一般服务端直接向客户端写，不用判断
//                                handleWrite(key);
//                            }
                        }
                    }

                    if (!isBoss && !queues[id].isEmpty()) { // worker线程处理IO
                        SocketChannel client = queues[id].take();
                        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024)); // attachment
                        System.out.println("Client " + client.socket().getPort() + "分配到worker-" + id);
                    }
            }
            } catch (IOException e) {

            } catch (InterruptedException er) {

            }


        }

        private void handleRead(SelectionKey key) {
            try {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer buf = (ByteBuffer)key.attachment();
                buf.clear();
                long bytesRead = client.read(buf);
                while(bytesRead > 0) {
                    buf.flip();
                    while(buf.hasRemaining()) {
                        //System.out.print((char)buf.get());
                        client.write(buf);
                    }
                    buf.clear();
                    bytesRead = client.read(buf);
                }
             //   System.out.println(bytesRead);
                if (bytesRead == -1) {
                    client.close();
                }
//                client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.allocate(1024)); // channel和buffer 1对1绑定
//                key.interestOps(SelectionKey.OP_WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleAccept(SelectionKey key) {

            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel client = ssc.accept();
                client.configureBlocking(false);
                System.out.println("Client " + client.socket().getPort() + " Connected!");
                int target = idx.getAndIncrement() % queues.length;
                queues[target].add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        TestCustomNetty netty = new TestCustomNetty();
        netty.initServer();
        NioThread boss = new NioThread(netty.selector1, netty.workerCount);
        NioThread worker1 = new NioThread(netty.selector2);
        NioThread worker2 = new NioThread(netty.selector2);
        boss.start();
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker1.start();
        worker2.start();

        try {
            boss.join();
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
