package top.lovelily.io.nio;

/*
 * @(#)NBTimeServer.java	1.4 01/12/13
 * A non blocking Internet time server implemented using
 * the New I/O (NIO) facilities added to J2SE v 1.4.
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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

// Listen on a port for connections and write back the current time.

/**
 *  nio featues:
 *      1. socket-->channel-->selector
 *      2.buffer
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/Channel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/SelectableChannel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/ServerSocketChannel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/Selector.html
 *
 */

public class NBTimeServer {
    private static final int DEFAULT_TIME_PORT = 8900;

    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();

    // Constructor with no arguments creates a time server on default port.
    public NBTimeServer() throws Exception {
        acceptConnections(this.DEFAULT_TIME_PORT);
    }

    // Constructor with port argument creates a time server on specified port.
    public NBTimeServer(int port) throws Exception {
        acceptConnections(port);
    }

    // Accept connections for current time. Lazy Exception thrown.
    private static void acceptConnections(int port) throws Exception {
        // Selector for incoming time requests
        Selector acceptSelector = SelectorProvider.provider().openSelector();

        // Create a new server socket and set to non blocking mode
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // todo: multiply serverSocketChannel ?
        ServerSocketChannel ssc1 = ServerSocketChannel.open();
        ssc1.configureBlocking(false);
        // 只有一个ServerSocketChannel
        System.out.println("ssc: " + ssc);
        System.out.println("ssc1: " + ssc1);
        // Bind the server socket to the local host and port

        InetAddress lh = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(lh, port);
        ssc.socket().bind(isa);

        // Register accepts on the server socket with the selector. This
        // step tells the selector that the socket wants to be put on the
        // ready list when accept operations occur, so allowing multiplexed
        // non-blocking I/O to take place.
        SelectionKey acceptKey = ssc.register(acceptSelector,
                SelectionKey.OP_ACCEPT);

        // selectionKey represent the registration of a selectable channel
        System.out.println(acceptKey);

        ssc1.register(acceptSelector, SelectionKey.OP_ACCEPT);

        int keysAdded = 0;
        System.out.println("waiting connecting....");
        // Here's where everything happens. The select method will
        // return when any operations registered above have occurred, the
        // thread has been interrupted, etc.

        while ((keysAdded = acceptSelector.select()) > 0) {
            System.out.println(keysAdded);
            // Someone is ready for I/O, get the ready keys
            Set readyKeys = acceptSelector.selectedKeys();
            Iterator i = readyKeys.iterator();

            // Walk through the ready keys collection and process date requests.
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey)i.next();
                System.out.println(sk); // equal to acceptKey
                i.remove();
                // The key indexes into the selector so you
                // can retrieve the socket that's ready for I/O
                ServerSocketChannel nextReady =
                        (ServerSocketChannel)sk.channel();
                System.out.println("nextReady: " + nextReady);
                // Accept the date request and send back the date string
                Date now = new Date();
                // write the current timestamp to buffer
                ByteBuffer writeBuf = ByteBuffer.allocateDirect(1024);
                writeBuf.putLong(now.getTime());

                SocketChannel socketChannel = nextReady.accept();
                socketChannel.configureBlocking(false);
               // Thread.sleep(10000l); // block client, client won't block
                // socketChannel.write(encoder.encode(CharBuffer.wrap(now.toString() + "\r\n")));
                StringBuffer sb = new StringBuffer(now.toString());
                for (int j = 0; j < 1000; j++) {
                    sb.append(now.toString());
                }
                int written = socketChannel.write(encoder.encode(CharBuffer.wrap(sb.toString() + "\r\n")));
                System.out.println("written: " + written);
                System.out.println("Do next thing");


            }
        }

    }

    // Entry point.
    public static void main(String[] args) {
        // Parse command line arguments and
        // create a new time server (no arguments yet)
        try {
            NBTimeServer nbt = new NBTimeServer();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}