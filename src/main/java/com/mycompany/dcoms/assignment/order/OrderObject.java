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
import java.sql.ResultSet;
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
    
    static final String ORDER_TABLE_NAME = "TBLORDER";
    static final String QUANTITY_COLUMN_NAME = "quantity";
    static final String USERNAME_COLUMN_NAME = "username";
    static final String PRODUCT_ID_COLUMN_NAME = "product_id";
    
    public OrderObject() throws RemoteException {
        super();
    }
    
    @Override
    public boolean createOrder(Order order) throws RemoteException {
        
        boolean success = false;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO " + ORDER_TABLE_NAME + ""
                    + "(" + QUANTITY_COLUMN_NAME + "," + USERNAME_COLUMN_NAME + "," + PRODUCT_ID_COLUMN_NAME +")"
                            + " VALUES (?, ?, ?)");
            statement.setInt(1, order.getQuantity());
            statement.setString(2, order.getUsername());
            statement.setInt(3, order.getProductId());
            statement.executeUpdate();
            success = true;
            
        } catch (SQLException ex ) {
            System.out.println(ex.getSQLState());
        }
        
        return success;
        
    }

    @Override
    public LinkedList<Order> getOrders(String username) throws RemoteException {
        
        LinkedList<Order> allOrders = new LinkedList<Order>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + ORDER_TABLE_NAME + " WHERE " + USERNAME_COLUMN_NAME + " = ?");
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            
            while(results.next()) {
                Integer fetchedOrderId = results.getInt(1);
                Integer fetchedQuantity = results.getInt(2);
                String fetchedUsername = results.getString(3);
                Integer fetchedProductId = results.getInt(4);
                Order fetchedOrder = new Order(fetchedOrderId, fetchedQuantity, fetchedUsername, fetchedProductId);
                allOrders.add(fetchedOrder);
            }
            
            
        } catch (SQLException ex) {}
        
        return allOrders;
        
    }
    
    
    
}
