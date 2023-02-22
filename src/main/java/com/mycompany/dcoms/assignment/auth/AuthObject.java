/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dcoms.assignment.auth;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author aakif
 */
public class AuthObject extends UnicastRemoteObject implements AuthInterface {

    String dbUrl = "jdbc:derby://localhost:1527/KGF";
    String dbUsername = "kgf";
    String dbPassword = "kgf";
    String tableName = "tblUser";
    
    AuthObject() throws RemoteException {
        super();
    }
    
    /**
     * Returns true if registration successful, and false if username already exists
     */
    @Override
    public boolean register(User user) throws RemoteException, UsernameExistsException {
        
        boolean success = false;
        
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ? VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, tableName);
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getIcNumber());
//            String query = "INSERT INTO " + tableName + " VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getIcNumber() + "')";
            statement.executeUpdate();
            success = true;
            
        }
        catch (SQLException ex) {
            // SQLState 23505 represents instance where primary key pre-exists in table
            String invalidPrimaryKeyErrorCode = "23505";
            if(ex.getSQLState().equals(invalidPrimaryKeyErrorCode) ) {
                throw new UsernameExistsException(ex);
            }
        }
        
        return success;
        
    }
    
    
    @Override
    public boolean login(String username, String password) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tableName + " WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet passwordResults = statement.executeQuery(query);
            
            if(passwordResults.next()) {
                success = true;
            }
            
        } catch (SQLException ex) {}
            
        return success;        
    
    }
}
