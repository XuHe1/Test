package top.lovelily.io.nio;

/*
 * @(#)TimeQuery.java	1.2 01/12/13
 * Ask a list of hosts what time it is.  Demonstrates NIO socket channels
 * (connection and reading), buffer handling, charsets, and regular
 * expressions.
 *
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Oracle or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;


public class TimeQuery {

    // The standard daytime port
    private static int DAYTIME_PORT = 8900;

    // The port we'll actually use
    private static int port = DAYTIME_PORT;

    // Charset and decoder for US-ASCII
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Direct byte buffer for reading
    private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

    // todo: buffer使用
    // CharBuffer   ByteBuffer  IntBuffer ShortBuffer LongBuffer   FloatBuffer     DoubleBuffer

    // Ask the given host what time it is
    //
    private static void query(String host) throws IOException {
        InetSocketAddress isa
                = new InetSocketAddress(InetAddress.getByName(host), port);

        ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

        // async
        AsynchronousSocketChannel asc = AsynchronousSocketChannel.open();
        asc.connect(isa);
        Future<Integer> future =  asc.read(dbuf);
        // future.get();



        SocketChannel sc = null;
        // Direct byte buffer for reading
        //ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);
        try {

            // Connect
            sc = SocketChannel.open();
            sc.configureBlocking(false); //
            sc.connect(isa); // non-blocking: connect() return right now, may fail to connect, must call next step
            sc.finishConnect();
            System.out.println(sc.isBlocking());


            // Read the time from the remote host.  For simplicity we assume
            // that the time comes back to us in a single packet, so that we
            // only need to read once.
            dbuf.clear();
            sc.read(dbuf); // non-block: 立即返回， block: 阻塞直到读取到内容
            // Print the remote address and the received time
            dbuf.flip();  // 复位，读操作需要复位
            CharBuffer cb = decoder.decode(dbuf);
            System.out.println(Thread.currentThread().getName() + " : " + cb);

            System.out.println(Thread.currentThread().getName() + ": Do next thing");



        } finally {
            // Make sure we close the channel (and hence the socket)
            if (sc != null)
                sc.close();
        }
    }

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(1);

        String host = "192.168.2.22";
        port = 8900;

        for (int i = 0; i < 1; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        try {
                            query(host);
                        } catch (IOException x) {
                            System.err.println(host + ": " + x);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            latch.countDown();

        }

     }

}