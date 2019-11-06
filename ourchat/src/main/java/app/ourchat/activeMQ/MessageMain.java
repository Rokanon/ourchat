/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.activeMQ;

import app.ourchat.activeMQ.model.MessageReceiver;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author dark
 */
public class MessageMain implements Runnable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Thread thread = new Thread(new MessageMain());

        thread.start();

    }

    @Override
    public void run() {
//        MessageReceiver.receive();
    }

}
