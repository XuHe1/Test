package top.lovelily.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * HttpClient工具很多， 如 ：
 * 1. java自带的 HttpUrlConnection
 * 2. Apache HttpClient
 * 3. OkHttp
 * 4. Retrofit
 *
 * 基于http协议的封装和实现， 需考虑的问题：
 * 1. session、cookie、header、method、httpEntity
 * 2. 异常恢复
 * 3. retry
 * 4. connection pool(connection manager)
 * 对于请求比较少的，使用完直接close, 但如果请求比较频繁，每次创建的话就会消耗资源 ， 原理类似 DBCP
 *
 * Author: xuhe
 * Date: 2019/12/5 6:04 下午
 * Version: 1.0
 */

public class TestHttpClient {
    static Log log = LogFactory.getLog(TestHttpClient.class);
    public static void main(String[] args) throws InterruptedException {

        log.debug("LOG Worked!");
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");

        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");

        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire.header", "debug");

        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");


        HttpGet get = new HttpGet("http://treasure.test.getqood.com/vehicles/1");
        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom().
                setConnectionManager(connManager).build();

        MultiHttpClientConnThread thread1
                = new MultiHttpClientConnThread(client, get);
        MultiHttpClientConnThread thread2
                = new MultiHttpClientConnThread(client, get);
        MultiHttpClientConnThread thread3
                = new MultiHttpClientConnThread(client, get);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
    }
}
