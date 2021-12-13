package top.lovelily.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Desc: RemoteServer
 * Author: Xu He
 * created: 2021/12/13 10:55
 */

public class RemoteServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname","192.168.50.56");

            Hello obj = new EvilObject();
            Hello remote  = (Hello) UnicastRemoteObject.exportObject(obj, 8999);

            System.out.println("启动 RMI 服务端");
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("evil", remote);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
