/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.activeMQ.model;

import app.ourchat.activeMQ.conf.MessagingConf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;

/**
 *
 * @author dark
 */
public class MessageReceiver implements Runnable {

    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String queueName = "message_queue";

    @Override
    public void run() {
        receive();
    }
    
    private void receive() {
        System.out.println("/***** MESSAGE CONSUMER STARTED *****/");

        MessagingConf conf = new MessagingConf();
        try {
            MessageConsumer messageConsumer = conf.messageConsumer();
            while (true) {
                System.out.println("/***** MESSAGE CONSUMED *****/");
                Message message = messageConsumer.receive();
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Message: " + textMessage.getText() + ", received succesfully to the queue \"" + queueName + "\"");
                    System.out.println("senderName: " + textMessage.getStringProperty("senderName"));
                    System.out.println("senderId: " + textMessage.getLongProperty("senderId"));
                    System.out.println("receiverId: " + textMessage.getLongProperty("receiverId"));
                }
            }

        } catch (JMSException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conf.close();
            } catch (JMSException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
