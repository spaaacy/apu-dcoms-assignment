/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import static com.mycompany.dcoms.assignment.Server.SOCKET_PORT;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aakif
 */
public class ProductObject extends UnicastRemoteObject implements ProductInterface{
    
    static final String DB_URL = "jdbc:derby://localhost:1527/KGF";
    static final String DB_USERNAME = "kgf";
    static final String DB_PASSWORD = "kgf";
    
    static final String PRODUCT_TABLE_NAME = "TBLPRODUCT";
    static final String PRODUCT_ID_COLUMN_NAME = "product_id";
    
    public ProductObject() throws RemoteException {
        super();
    }

    @Override
    public void getAllProducts() throws RemoteException {
        
        LinkedList<Product> fetchedProducts = new LinkedList<Product>();
        ServerSocket ss = null;
        Socket socket = null;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            ss = new ServerSocket(SOCKET_PORT);
            socket = ss.accept();
            
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + PRODUCT_TABLE_NAME);
            ResultSet results = statement.executeQuery();
            
            while(results.next()) {
                Integer fetchedProductId = results.getInt(1);
                String fetchedProductName = results.getString(2);
                Double fetchedPrice = results.getDouble(3);
                Integer fetchedTotalSupply = results.getInt(4);
                
                Product fetchedProduct = new Product(fetchedProductId, fetchedProductName, fetchedPrice, fetchedTotalSupply);
                fetchedProducts.add(fetchedProduct);
            }
            
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getSQLState());
        } catch (IOException ex ) {
            System.out.println("IOException");
        } finally {
            
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(fetchedProducts);
                oos.flush();
                oos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            }
            
        }
    }

    /**
     * Fetches product details using product ID, will return a NULL pointer if not matches found
     */
    @Override
    public void getProduct() throws RemoteException {

        Product fetchedProduct = null;
        ServerSocket ss = null;
        Socket socket = null;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            ss = new ServerSocket(SOCKET_PORT);
            socket = ss.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Integer productId = dis.readInt();
            
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + PRODUCT_ID_COLUMN_NAME + " = ?");
            statement.setInt(1, productId);
            ResultSet results = statement.executeQuery();
            
            while(results.next()) {
                Integer fetchedProductId = results.getInt(1);
                String fetchedProductName = results.getString(2);
                Double fetchedPrice = results.getDouble(3);
                Integer fetchedTotalSupply = results.getInt(4);
                
                fetchedProduct = new Product(fetchedProductId, fetchedProductName, fetchedPrice, fetchedTotalSupply);
            }
            
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getSQLState());
        } catch (IOException ex ) {
            System.out.println("IOException");
        } finally {
            
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(fetchedProduct);
                oos.flush();
                oos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            }
            
        }
        
    }
    
    
    
}
