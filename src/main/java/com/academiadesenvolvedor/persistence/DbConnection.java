package com.academiadesenvolvedor.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
   private static final String url = "jdbc:mysql://127.0.0.1:3306/loja_churras";
    private static final String username = "root";
    private static final String password  = "123";
    public static Connection getConnection(){

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void closeConnection(Connection conn){
       try{
           conn.close();
       }catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public static  void rollbackChanges(Connection conn){
        try {
            conn.rollback();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
