package top.lovelily.io.nio2;

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

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.jar.JarOutputStream;

// Listen on a port for connections and write back the current time.

/**
 *
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/Channel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/SelectableChannel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/ServerSocketChannel.html
 * https://docs.oracle.com/javase/6/docs/api/java/nio/channels/Selector.html
 *
 */

public class AIOTimeServer {
    private static final int DEFAULT_TIME_PORT = 8900;

    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();

    // Constructor with no arguments creates a time server on default port.
    public AIOTimeServer() throws Exception {
        acceptConnections(this.DEFAULT_TIME_PORT);
    }

    // Constructor with port argument creates a time server on specified port.
    public AIOTimeServer(int port) throws Exception {
        acceptConnections(port);
    }

    // Accept connections for current time. Lazy Exception thrown.
    private void acceptConnections(int port) throws Exception {
        // Create a new server socket and set to non blocking mode
        AsynchronousServerSocketChannel  ssc = AsynchronousServerSocketChannel.open();

        // Bind the server socket to the local host and port
        InetAddress lh = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(lh, port);
        ssc.bind(isa);

        ssc.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOTimeServer>() {
            @Override
            public void completed(AsynchronousSocketChannel result, AIOTimeServer attachment) {
                //attachment.
                System.out.println("completed");
//                ByteBuffer byteBuffer = ByteBuffer.allocate(100);
//                result.read(byteBuffer);
//                System.out.println(new String(byteBuffer.array()));
                // GET / HTTP/1.1
                //Host: localhost:8900
                //Connection: keep-alive
                //Cache-Control: max-age=0
                //sec-ch-ua: "


                String responseDocument = ("<html><body>" +
                        "AIO Server: " +  System.currentTimeMillis() + "</body></html>");

                String responseHeader = ("HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n" +
                        "Content-Length: " + responseDocument.length() +
                        "\r\n\r\n");

                try {
                    result.write(encoder.encode(CharBuffer.wrap(responseHeader)));
                    result.write(encoder.encode(CharBuffer.wrap(responseDocument)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable exc, AIOTimeServer attachment) {
                System.out.println("failed");
            }
        });
    }

    // Entry point.
    public static void main(String[] args) {
        // Parse command line arguments and
        // create a new time server (no arguments yet)
        try {
            AIOTimeServer nbt = new AIOTimeServer();
            System.out.println("已经启动了");
            Thread.sleep(10000l);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}