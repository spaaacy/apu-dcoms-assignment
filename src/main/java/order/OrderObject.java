/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public class OrderObject extends UnicastRemoteObject implements OrderInterface {

    String dbUrl = "jdbc:derby://localhost:1527/KGF";
    String dbUsername = "kgf";
    String dbPassword = "kgf";
    String tableName = "order";
    
    @Override
    public boolean createOrder(int product_id, String username, int quantity) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            
            Statement statement = conn.createStatement();
            String query = "INSERT INTO " + tableName + " VALUES ()";
            statement.executeUpdate(query);
            
            
        } catch (SQLException ex ) {}
        
        return success;
        
    }

    @Override
    public void getOrders(String username) throws RemoteException {
    }
    
    
    
}
