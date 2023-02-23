/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.order;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public class OrderObject extends UnicastRemoteObject implements OrderInterface {

    static final String DB_URL = "jdbc:derby://localhost:1527/KGF";
    static final String DB_USERNAME = "kgf";
    static final String DB_PASSWORD = "kgf";
    static final String ORDER_TABLE_NAME = "order";
    
    OrderObject() throws RemoteException {
        super();
    }
    
    @Override
    public boolean createOrder(Order order) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            PreparedStatement statement = conn.prepareStatement("INSERT INTO " + ORDER_TABLE_NAME + ""
                    + "(quantity, username, product_id) VALUES (?, ?, ?)");
            statement.setInt(1, order.getQuantity());
            statement.setString(2, order.getUsername());
            statement.setInt(3, order.getProductId());
            statement.executeUpdate();
            success = true;
            
        } catch (SQLException ex ) {}
        
        return success;
        
    }

    @Override
    public LinkedList<Order> getOrders(String username) throws RemoteException {
        return new LinkedList<Order>();
    }
    
    
    
}
