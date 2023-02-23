/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dcoms.assignment.auth;

import static com.mycompany.dcoms.assignment.auth.UsernameExistsException.SQL_PRIMARY_KEY_ERROR_CODE;
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

/**
 *
 * @author aakif
 */
public class AuthObject extends UnicastRemoteObject implements AuthInterface {

    static final String DB_URL = "jdbc:derby://localhost:1527/KGF";
    static final String DB_USERNAME = "kgf";
    static final String DB_PASSWORD = "kgf";
    
    static final String USER_TABLE_NAME = "TBLUSER";
    static final String USERNAME_COLUMN_NAME = "username";
    static final String PASSWORD_COLUMN_NAME = "password";
    
    public AuthObject() throws RemoteException {
        super();
    }
    
    /**
     * Returns true if registration successful, and false if username already exists
     */
    @Override
    public boolean register(User user) throws RemoteException, UsernameExistsException {
        
        boolean success = false;
        
        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);) {
            
            PreparedStatement statement = conn.prepareStatement("INSERT INTO " + USER_TABLE_NAME + " VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getIcNumber());
            statement.executeUpdate();
            success = true;
            
        }
        catch (SQLException ex) {
            // SQLState 23505 represents instance where primary key pre-exists in table
            
            if(ex.getSQLState().equals(SQL_PRIMARY_KEY_ERROR_CODE) ) {
                throw new UsernameExistsException(ex);
            }
        }
        
        return success;
        
    }
    
    
    /**
     * Returns true if login successful, and false if credentials are invalid
     */
    @Override
    public boolean login(String username, String password) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);) {
            
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + USER_TABLE_NAME + " WHERE " 
                    + USERNAME_COLUMN_NAME + " = ? AND " + PASSWORD_COLUMN_NAME + " = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            
            if(results.next()) {
                success = true;
            }
            
        } catch (SQLException ex) {}
            
        return success;        
    
    }
}
