package top.lovelily.remote;

/**
 * Desc: ComputeEngine
 * Author: Xu He
 * created: 2021/12/13 16:23
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ComputeEngine implements Compute {

    static {
        System.out.println("执行了恶意代码");
    }

    public ComputeEngine() {
        super();
    }

    public <T> T executeTask(Task<T> t) {
        return t.execute();
    }

    public static void main(String[] args) {
        // -Djava.security.debug=access,failure -Djava.security.manager -Djava.security.policy=E:\IdeaProjects\Test\src\main\resources\my.policy
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Compute engine = new ComputeEngine();
            // 这就话是必须的！官方案例有错误
            LocateRegistry.createRegistry(9999);
            Compute stub =
                    (Compute) UnicastRemoteObject.exportObject(engine, 9999);

            Registry registry = LocateRegistry.getRegistry(9999);
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}