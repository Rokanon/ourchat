/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.activeMQ.model;

import app.ourchat.activeMQ.conf.MessagingConf;
import app.ourchat.profile.beans.ProfileSessionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;

/**
 *
 * @author dark
 */
public class MessageSender {

    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String queueName = "message_queue";

    public static void send(String message, Long receiverId) {

        System.out.println("/***** MESSAGE PRODUCER CALLED *****/");

        MessagingConf conf = new MessagingConf();
        try {
            Session session = conf.session();

            TextMessage textMessage = session.createTextMessage(message == null ? "" : message);

            ProfileSessionHandler profileSessionHandler = new ProfileSessionHandler();

            if (profileSessionHandler.getLoggedIn()) {
                textMessage.setStringProperty("senderName", profileSessionHandler.getProfile().getName());
                textMessage.setLongProperty("senderId", profileSessionHandler.getProfile().getId());
                textMessage.setLongProperty("receiverId", receiverId);
            } else {
                textMessage.setStringProperty("senderName", "Anonymus");
                textMessage.setLongProperty("senderId", 0L);
                textMessage.setLongProperty("receiverId", receiverId);
            }

            MessageProducer messageProducer = conf.messageProducer();

            messageProducer.send(textMessage);

        } catch (JMSException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conf.close();
            } catch (JMSException ex) {
                Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
