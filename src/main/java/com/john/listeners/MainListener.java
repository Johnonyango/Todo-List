package com.john.listeners;

import com.john.JdbcUtill.DbConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class MainListener implements ServletContextListener {
    DbConnection dbConnection;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Listeners**********");
        try {
            dbConnection = new DbConnection();
            ServletContext scx = sce.getServletContext();
            scx.setAttribute("dbConnection", dbConnection);
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dbConnection==null)
            return;
        try{
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
