/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.order;

import static com.mycompany.dcoms.assignment.Server.SOCKET_PORT_NUMBER;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void createOrder() throws RemoteException {
        
        boolean success = false;
        ServerSocket ss = null;
        Socket socket = null;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            ss = new ServerSocket(SOCKET_PORT_NUMBER);
            socket = ss.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Order order = (Order)ois.readObject();
            
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
            System.out.println("SQLException: " + ex.getSQLState());
        } catch (IOException ex ) {
            System.out.println("IOException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } finally {
            
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeBoolean(success);
                dos.flush();
                dos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            }
        }
        
    }

    @Override
    public void getOrders() throws RemoteException {
        
        LinkedList<Order> allOrders = new LinkedList<Order>();
        ServerSocket ss = null;
        Socket socket = null;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            ss = new ServerSocket(SOCKET_PORT_NUMBER);
            socket = ss.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String username = dis.readUTF();
            
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
            
            
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getSQLState());
        } catch (IOException ex) {
            System.out.println("IOException");
        }finally {
            
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(allOrders);
                oos.flush();
                oos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            }
            
        }
        
    }
    
    
    
}
