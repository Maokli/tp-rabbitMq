package com.mycompany.app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class InformationHandler {
    private final static String QUEUE_NAME = "Information_queue";
    public static void main( String[] args )
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();Channel channel = connection.createChannel())
        {
            channel.queueDeclare (QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Information Consumer recieved: " + message);
            };
            channel.basicConsume(QUEUE_NAME, deliverCallback, consumertag -> {});
            ListenInfinitely();
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    }

    // keeps an infinite loop running so the system doesn't exit
    private static void ListenInfinitely()
    {
        while(true) {}
    }
}
