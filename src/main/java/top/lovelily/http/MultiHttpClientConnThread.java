package top.lovelily.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Desc: MultiHttpClientConnThread
 * Author: xuhe
 * Date: 2019/12/9 5:12 下午
 * Version: 1.0
 */
//@AllArgsConstructor
//@Data
public class MultiHttpClientConnThread extends Thread {
    private CloseableHttpClient client;
    private HttpGet get;

    // standard constructors
    public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
        this.client = client;
        this.get = get;
    }
    public void run(){
        try {
            HttpResponse response = client.execute(get);
            EntityUtils.consume(response.getEntity());
        } catch (ClientProtocolException ex) {
        } catch (IOException ex) {
        }
    }
}