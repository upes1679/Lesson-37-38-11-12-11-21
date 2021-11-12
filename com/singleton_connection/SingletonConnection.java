package com.singleton_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private int instanceCount=0;
    private static SingletonConnection instance;
    private static String connectionString = "jdbc:sqlite:C:\\Users\\markf\\IdeaProjects\\FirstJDBC\\DB\\myfirstdb.db";
    private Connection connection;

    private SingletonConnection(){
        try {
            this.connection= DriverManager.getConnection(connectionString);
            instanceCount++;
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }

    public static SingletonConnection getInstance() throws SQLException {
       if(instance==null){
           instance=new SingletonConnection();
       }else if(instance.getConnection().isClosed()){
           instance=new SingletonConnection();
       }
       return instance;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public Connection getConnection() {
        return connection;
    }
}
