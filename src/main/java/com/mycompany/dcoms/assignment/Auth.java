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
    
    /**
     * Returns true if registration successful, and false if username already exists
     */
    @Override
    public boolean register(String username, String password, String firstName, String lastName) throws UsernameExistsException {
        
        boolean success = false;
        
        try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            Statement statement = conn.createStatement();
            String query = "INSERT INTO USERS VALUES ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "')";
            statement.executeUpdate(query);
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
    public boolean login(String username, String password) {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);) {
            Statement statement = conn.createStatement();
            String query = "SELECT password FROM USERS WHERE username = '" + username + "'";
            ResultSet passwordResults = statement.executeQuery(query);
            passwordResults.next();
            String fetchedPassword = passwordResults.getString(1);
            System.out.println("Password: " + fetchedPassword);
            success = fetchedPassword.equals(password);
        } catch (SQLException e) {}
            
        return success;        
    
    }
}
