package com.etianxia.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.Iterator;

/**
 * Desc: TcpServer
 * Author: xuhe
 * Date: 2018/9/11 下午2:20
 * Version: 1.0
 */

public class TcpServer {
    private static final int BUF_SIZE=1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();
    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept(); // 不同的连接
        sc.configureBlocking(false);
        // 重新注册到selector, 事件更改为可写
        sc.register(key.selector(), SelectionKey.OP_WRITE,ByteBuffer.allocateDirect(BUF_SIZE));

    }

    public static void handleRead(SelectionKey key) throws IOException{
        System.out.println("READ...");
        key.interestOps(SelectionKey.OP_WRITE);
        SocketChannel sc = (SocketChannel)key.channel();
        System.out.println(sc.isBlocking());
        ByteBuffer buf = (ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.register(key.selector(), SelectionKey.OP_WRITE,ByteBuffer.allocateDirect(BUF_SIZE));
            key.interestOps(SelectionKey.OP_WRITE);
            // sc.close();
        }
    }

    public static void handleWrite(SelectionKey key) throws IOException{
        System.out.println("Write...");
//        ByteBuffer buf = (ByteBuffer)key.attachment();
//        while(buf.hasRemaining()){
//        }
//        buf.compact();
//        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        Date now = new Date();
        sc.write(encoder.encode(CharBuffer.wrap(now.toString() + "\r\n")));
        sc.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
            selector = Selector.open();
            ssc= ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                if(selector.select(TIMEOUT) == 0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while(iter.hasNext()){
                    SelectionKey key = iter.next();
                    if(key.isAcceptable()){
                        System.out.println("isAcceptable = true");
                        handleAccept(key);
                    }
                    if(key.isReadable()){
                        System.out.println("isReadable = true");
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
                        System.out.println("isWritable = true");
                        handleWrite(key);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }
                if(ssc!=null){
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        selector();
    }

}