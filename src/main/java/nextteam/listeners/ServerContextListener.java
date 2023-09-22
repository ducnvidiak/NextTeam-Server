/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import nextteam.Global;

/**
 *
 * @author vnitd
 */
public class ServerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Server started!");
        Global.workingPath = sce.getServletContext().getRealPath("/");
        Global.setDAOConnection();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Server stopped!");

        Global.closeDAOConnection();
    }
}
