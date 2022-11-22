/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author PC
 */
public class DBConnect {

    public static Connection getConnection() {
        Connection cons = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            cons = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=quanlinew;user=sa;password=123");
//            Class.forName("com.mysql.jdbc.Driver");
            cons = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_qlhv", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cons;
    }
    
    public static void disconnect() {
        Connection cons = null;
        if (cons != null) {
            try {
                cons.close();
                cons = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
    
}
