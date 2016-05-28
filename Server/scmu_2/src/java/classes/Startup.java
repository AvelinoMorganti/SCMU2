/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.servlet.ServletContextEvent;

/**
 *
 * @author Avelino
 */
public class Startup implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent context) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Startup!!!!");
        WServerSocket asd = new WServerSocket();
        asd.initialize();
    }
}
