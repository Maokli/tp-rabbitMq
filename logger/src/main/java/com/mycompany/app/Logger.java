package com.mycompany.app;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.rabbitmq.client.Channel;
/**
 * Hello world!
 *
 */
public class Logger 
{
    private final static String ERROR_QUEUE_NAME = "Errors_queue";
    private final static String INFORMATION_QUEUE_NAME = "Information_queue";
    private final static String ERROR_Event = "1";
    private final static String INFORMATION_Event = "2";
    public static void main( String[] args )
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();Channel channel = connection.createChannel())
        {
            channel.queueDeclare (ERROR_QUEUE_NAME, false, false, false, null);
            channel.queueDeclare (INFORMATION_QUEUE_NAME, false, false, false, null);
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         
                // Reading data using readLine
                System.out.println("Enter log type (ERROR / INFORMATION): ");
                System.out.println(ERROR_Event + "- ERROR");
                System.out.println(INFORMATION_Event + "- Information");
                String logType = reader.readLine();
                if(!logType.equals(ERROR_Event) && !logType.equals(INFORMATION_Event))
                {
                    System.out.println("Invalid choice!");
                    continue;
                }
                System.out.println("Enter log: ");
                String message = reader.readLine();
        
                // Printing the read line
                System.out.println("Producer sent: " + message);
                String queueToUse = logType.equals(ERROR_Event) ? ERROR_QUEUE_NAME : INFORMATION_QUEUE_NAME;

                channel.basicPublish("", queueToUse, null, message.getBytes());
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    }
}
