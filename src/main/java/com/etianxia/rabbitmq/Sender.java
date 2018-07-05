package com.etianxia.rabbitmq;

import com.etianxia.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by kaiitsugyou on 16/12/21.
 */
public class Sender {
    private final static String QUEUE_NAME = "data-server.proto";
    private final static String exchange = "data-server";
    private final static String routingKey = "MAIN.DT.#"; // 会通配 MAIN.DT.NEW.#
    //private final static String routingKey = "MAIN.DT.NEW.#";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("xuhe");
        factory.setPassword("123456");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello World!";
        //channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.basicPublish(exchange, routingKey, null, message.getBytes());
       // channel.basicPublish(message.getBinding().getExchange().getName(), (String)message.getBinding().getRoutingKey().get(0), message.getProperties(), (byte[])message.getBody());
        User user  = new User(1, "xuhe");
        //channel.basicPublish("", QUEUE_NAME, null, (byte[])user);
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
