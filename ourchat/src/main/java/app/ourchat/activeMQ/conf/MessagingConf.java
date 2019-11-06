/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.activeMQ.conf;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author dark
 */
public class MessagingConf {

    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    private static final String MESSAGE_QUEUE = "message_queue";

    private Connection connection;
    private Session session;
    private Destination destination;

//    private MessageReceiver messageReceiver;
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
    }

    public Connection connection() throws JMSException {
        if (null == connection) {
            connection = connectionFactory().createConnection();
            connection.start();
        }
        return connection;
    }

    public Session session() throws JMSException {
        if (null == session) {
            session = connection().createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        }
        return session;
    }

    public Destination destination() throws JMSException {
        if (null == destination) {
            destination = session().createQueue(MESSAGE_QUEUE);
        }
        return destination;
    }

    public MessageProducer messageProducer() throws JMSException {
        return session().createProducer(destination());
    }

    public MessageConsumer messageConsumer() throws JMSException {
        return session().createConsumer(destination());
    }

    public void sendTextMessage(String message) throws JMSException {
        System.out.println("Message: " + message + ", sent succesfully to the queue \"" + MESSAGE_QUEUE + "\"");
        messageProducer().send(session().createTextMessage(message));

    }

    public String receiveTextMessage() throws JMSException {
        return ((TextMessage) messageConsumer().receive()).getText();
    }

    public void close() throws JMSException {
        if (null != session()) {
            session().close();
        }
        if (null != connection()) {
            connection().close();
        }
    }

}
