/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.startup;

import app.ourchat.activeMQ.model.MessageReceiver;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author dark
 */
@WebListener
public class AppStartup implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("/***** INITIALIZED  *****/");
        startMessageConsumer();
        System.out.println("/***** INITIALIZED FINISHED *****/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("/***** DESTROYED *****/");
    }

    private void startMessageConsumer() {
        new Thread(new MessageReceiver()).start();
    }

}
