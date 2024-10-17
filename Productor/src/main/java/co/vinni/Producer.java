package co.vinni;

import co.jsbm.Evento;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer {
    private final static String QUEUE_NAME = "topic_sms";
    private final static String TASK_QUEUE_NAME = "task_queue";
    private final static String EVENT_QUEUE_NAME = "event_queue";
    private final static String server = "127.0.0.1";

    public static void sendEvent(Evento event) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(EVENT_QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", EVENT_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    event.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [" + EVENT_QUEUE_NAME + "] Envíado '" + event.toString() + "'");
        }
    }

    public static void sendMsg(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
    public static void sendTask(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
