package top.lovelily.io.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Desc: WorkerRunnable
 * Author: xuhe
 * Date: 2019/6/18 7:15 PM
 * Version: 1.0
 */

public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
//            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
//                    this.serverText + " - " +
//                    time +
//                    "").getBytes());

            byte[] responseDocument = ("<html><body>" +
                    serverText + ": " +  time + "</body></html>").getBytes("UTF-8");

            byte[] responseHeader = ("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: " + responseDocument.length +
                    "\r\n\r\n").getBytes("UTF-8");

            // 根据http协议， 必须先发 header，再发body
            output.write(responseHeader);
            output.write(responseDocument);
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}