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
import model.Order;

/**
 *
 * @author aakif
 */
public class OrderObject extends UnicastRemoteObject implements OrderInterface {

    String dbUrl = "jdbc:derby://localhost:1527/KGF";
    String dbUsername = "kgf";
    String dbPassword = "kgf";
    String tableName = "order";
    
    OrderObject() throws RemoteException {
        super();
    }
    
    @Override
    public boolean createOrder(Order order) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            
            Statement statement = conn.createStatement();
            String query = "INSERT INTO " + tableName + " VALUES ()";
            statement.executeUpdate(query);
            
            
        } catch (SQLException ex ) {}
        
        return success;
        
    }

    @Override
    public LinkedList<Order> getOrders(String username) throws RemoteException {
        return new LinkedList<Order>();
    }
    
    
    
}
