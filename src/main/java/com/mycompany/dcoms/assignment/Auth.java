/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dcoms.assignment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aakif
 */
public class Auth extends UnicastRemoteObject implements AuthInterface {

    String dbUrl = "jdbc:derby://localhost:1527/KGF";
    String dbUsername = "kgf";
    String dbPassword = "kgf";
    
    Auth() throws RemoteException {
        super();
    }
    
    @Override
    public boolean register(String firstName, String lastName, String username, String password) {
        // TODO: If username exists return false
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            Statement statement = conn.createStatement();
            String query = "INSERT INTO USERS VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "')";
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException ex) {}
        return false;
    }
    
    @Override
    public boolean login(String username, String password) throws MultipleUserException {
        
        boolean success = false;
        
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            Statement statement = conn.createStatement();
//            String query = "SELECT * FROM USERS WHERE username = '" + username + "'";
            String query = "SELECT * FROM USERS WHERE first_name = 'aakif'";
            ResultSet rs = statement.executeQuery(query);
            
            if (rs.getFetchSize() > 1) {
                throw new MultipleUserException();
            }
            
            while(rs.next()) {
                String fetchedPassword = rs.getString(2);
                success = fetchedPassword.equals(password);
            }
        }
        catch (SQLException ex) {}
        
        return success;        
    
    }
}
