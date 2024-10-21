package co.vinni;

import co.jsbm.ManejaArchivo;
import co.vinni.colaEventos.EventsManage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receiver {
    private final static String QUEUE_NAME = "topic_sms";
    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static String EVENT_QUEUE_NAME = "event_queue";
    private final static String server = "127.0.0.1";

    public static void receiveEvent() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(EVENT_QUEUE_NAME, true, false, false, null);
        System.out.println(" [" + EVENT_QUEUE_NAME + "] Esperando peticiones. To exit press CTRL+C");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [" + EVENT_QUEUE_NAME + "] recibido '" + message + "'");
            try {
                guardarEvento(message);
            }catch (Exception e)
            {
                System.err.println("Error");
            }
            finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(EVENT_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    public static void receive() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
    public static void receiveWork() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(server);
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void guardarEvento(String mensaje) {
        try {
            ManejaArchivo ma = new ManejaArchivo("bd.txt");
            ma.setMensaje(mensaje);
            ma.Save();
            if(ma.isError()){
                EventsManage em = new EventsManage();
                System.err.println(" [" + EVENT_QUEUE_NAME + "] Error -> " + ma.getRespuesta());
                em.sentEventString(mensaje);
            }else{
                System.out.println(" [" + EVENT_QUEUE_NAME + "] Echo!");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
