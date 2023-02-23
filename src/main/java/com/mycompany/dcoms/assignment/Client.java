/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.User;
import com.mycompany.dcoms.assignment.auth.UsernameExistsException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mycompany.dcoms.assignment.order.Order;
import com.mycompany.dcoms.assignment.order.OrderInterface;
import com.mycompany.dcoms.assignment.product.Product;
import com.mycompany.dcoms.assignment.product.ProductInterface;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public class Client {
    
    static final String AUTH_SERVER_NAME = "auth";
    static final String ORDER_SERVER_NAME = "order";
    static final String PRODUCT_SERVER_NAME = "product";
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        
        AuthInterface authObject = (AuthInterface)Naming.lookup("rmi://localhost:1050/" + AUTH_SERVER_NAME);
        OrderInterface orderObject = (OrderInterface)Naming.lookup("rmi://localhost:1050/" + ORDER_SERVER_NAME);
        ProductInterface productObject = (ProductInterface)Naming.lookup("rmi://localhost:1050/" + PRODUCT_SERVER_NAME);
           
        boolean success = false;
        
        /**
         * Sample Register
         */
        System.out.println("\tREGISTER");
        success = false;
        try {
            User newUser = new User("spacy", "abc123", "aakif", "ahamath", 123456);
            success = authObject.register(newUser);
        } catch (UsernameExistsException ex) {
            System.out.println("User already exists.");
        } finally {
            System.out.println("Register successful: " + success);
        }
        
        /**
         * Sample Login
         */
        System.out.println("\n\tLOGIN");
        success = authObject.login("spacy", "abc123");
        System.out.println("Login successful: " + success);
      
        /**
         * Sample Create Order
         */
        System.out.println("\n\tCREATE ORDER");
        Order newOrder = new Order(22, "spacy", 4);
        success = orderObject.createOrder(newOrder);
        System.out.println("Order successful: " + success);

        /**
         * Sample Get Orders
         */
        System.out.println("\n\tGET ORDERS");
        LinkedList<Order> allOrders = orderObject.getOrders("spacy");
        for (Order nextOrder: allOrders) {
            System.out.println("Order ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity() + 
                    "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
        }
        
        /**
         * Sample Get Product
         */
        System.out.println("\n\tGET SINGLE PRODUCT");
        Product fetchedProduct = productObject.getProduct(1);
        try {
            System.out.println("Product ID: " + fetchedProduct.getProductId() + "\nProduct Name: " + fetchedProduct.getProductName() + 
                    "\nPrice: " + fetchedProduct.getPrice() + "\nTotal Supply: " + fetchedProduct.getTotalSupply());
        } catch (NullPointerException ex) {
            System.out.println("Product not found!");
        }

        /**
         * Sample Get Products
         */
        System.out.println("\n\tGET ALL PRODUCTS");
        LinkedList<Product> fetchedProducts = productObject.getAllProducts();
        for(Product nextProduct: fetchedProducts) {
            System.out.println("Product ID: " + nextProduct.getProductId() + "\nProduct Name: " + nextProduct.getProductName() + 
                    "\nPrice: " + nextProduct.getPrice() + "\nTotal Supply: " + nextProduct.getTotalSupply() + "\n");
        }
        
        
    }
    
}
