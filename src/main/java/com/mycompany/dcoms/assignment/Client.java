/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import static com.mycompany.dcoms.assignment.Server.SERVER_PORT_NUMBER;
import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.User;
import com.mycompany.dcoms.assignment.auth.NonUniqueDetailsExeception;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mycompany.dcoms.assignment.order.Order;
import com.mycompany.dcoms.assignment.order.OrderInterface;
import com.mycompany.dcoms.assignment.product.Product;
import com.mycompany.dcoms.assignment.product.ProductInterface;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aakif
 */
public class Client {
    
    static final String AUTH_SERVER_NAME = "auth";
    static final String ORDER_SERVER_NAME = "order";
    static final String PRODUCT_SERVER_NAME = "product";
    
    static final Integer SERVER_PORT_NUMBER = 1050;    
    static final Integer SOCKET_PORT_NUMBER = 1060;
    static final String SERVER_ADDRESS = "rmi://localhost:" + SERVER_PORT_NUMBER;
   
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, InterruptedException {
        
        
        AuthInterface authObject = (AuthInterface)Naming.lookup(SERVER_ADDRESS + "/" + AUTH_SERVER_NAME);
        OrderInterface orderObject = (OrderInterface)Naming.lookup(SERVER_ADDRESS + "/" + ORDER_SERVER_NAME);
        ProductInterface productObject = (ProductInterface)Naming.lookup(SERVER_ADDRESS + "/" + PRODUCT_SERVER_NAME);
           
        /**
         * Sample Register
         */
        System.out.println("\tREGISTER");
        
        // Makes the call and output the results
        Runnable registerThread1 = () -> {
                boolean success = false;
                try {
                    success = authObject.register();
                } catch (NonUniqueDetailsExeception ex) {
                    System.out.println("Username/IC already exists");
                } catch (RemoteException ex) {
                } finally {
                    System.out.println("Register successful: " + success);
                }
        };
        
        // Only connects to socket to feed data
        Runnable registerThread2 = () -> {
            User newUser = new User("test9", "abc1234", "aakif", "ahamath", (int)(Math.random()*10000));
            try {  
                Socket socket = new Socket("localhost", SOCKET_PORT_NUMBER);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(newUser);
                oos.flush();
                oos.close();
                socket.close();
            } catch (IOException ex) {}
        };
        
        // Executes both thread in correct order and waits for completion
        ScheduledExecutorService register = Executors.newScheduledThreadPool(2);
        register.submit(registerThread1);
        register.schedule(registerThread2, 500, TimeUnit.MILLISECONDS);
        register.shutdown();
        register.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        
        /**
         * Sample Login
         */
        System.out.println("\n\tLOGIN");
        boolean loginSuccess = authObject.login("spacy", "abc123");
        System.out.println("Login successful: " + loginSuccess);
      
        /**
         * Sample Create Order
         */
        System.out.println("\n\tCREATE ORDER");
        Order newOrder = new Order(22, "spacy", 4);
        boolean orderSuccess = orderObject.createOrder(newOrder);
        System.out.println("Order successful: " + orderSuccess);

        /**
         * Sample Get Orders
         */
        System.out.println("\n\tGET ORDERS");
        LinkedList<Order> allOrders = orderObject.getOrders("spacy");
        if(!allOrders.isEmpty()) {
                for (Order nextOrder: allOrders) {
                System.out.println("Order ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity() + 
                        "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
            }
        } else {
            System.out.println("No orders found!");
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
        LinkedList<Product> allProducts = productObject.getAllProducts();
        if (!allProducts.isEmpty()) {
            for(Product nextProduct: allProducts) {
                System.out.println("Product ID: " + nextProduct.getProductId() + "\nProduct Name: " + nextProduct.getProductName() + 
                        "\nPrice: " + nextProduct.getPrice() + "\nTotal Supply: " + nextProduct.getTotalSupply() + "\n");
            }
        } else {
            System.out.println("No products found!");
        }
        
        
                
    }
    
}
