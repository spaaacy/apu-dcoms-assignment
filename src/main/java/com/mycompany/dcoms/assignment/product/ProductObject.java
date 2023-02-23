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
    public LinkedList<Product> getAllProducts() throws RemoteException {
        
        LinkedList<Product> fetchedProducts = new LinkedList<Product>();
        
        return fetchedProducts;
        
    }

    @Override
    public Product getProduct(Integer productId) throws RemoteException {

        Product fetchedProduct = null;
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
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
            
        } catch (SQLException ex) {}
        
        return fetchedProduct;
        
    }
    
    
    
}
