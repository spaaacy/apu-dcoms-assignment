/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author aakif
 */
public class Main {
    
    public static void main(String[] args) {
        
        String username = "username2";
        String password = "password2";
        String firstName = "first2";
        String lastName = "last2";
        
        String databaseUrl = "jdbc:derby://localhost:1527/KGF";
        String dbUsername = "kgf";
        String dbPassword = "kgf";
    
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
            Statement statement = conn.createStatement();
            String query = "INSERT INTO USERS VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "')";
            statement.execute(query);

        } catch (SQLException ex) {}
    }
}
