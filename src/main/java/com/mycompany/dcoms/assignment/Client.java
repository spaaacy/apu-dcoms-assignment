/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.User;
import com.mycompany.dcoms.assignment.auth.NonUniqueDetailsExeception;
import com.mycompany.dcoms.assignment.order.Order;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mycompany.dcoms.assignment.order.OrderInterface;
import com.mycompany.dcoms.assignment.product.Product;
import com.mycompany.dcoms.assignment.product.ProductInterface;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
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
    
    static final Integer RMI_PORT = 1050;    
    static final Integer SOCKET_PORT = 1060;
    static final String HOST_ADDRESS = "localhost";
    static final String SERVER_ADDRESS = "rmi://" + HOST_ADDRESS + ":" + RMI_PORT;
   
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, InterruptedException {
        
        
        AuthInterface authObject = (AuthInterface)Naming.lookup(SERVER_ADDRESS + "/" + AUTH_SERVER_NAME);
        OrderInterface orderObject = (OrderInterface)Naming.lookup(SERVER_ADDRESS + "/" + ORDER_SERVER_NAME);
        ProductInterface productObject = (ProductInterface)Naming.lookup(SERVER_ADDRESS + "/" + PRODUCT_SERVER_NAME);
           
        /**
         * Sample Register
         */
        System.out.println("\tREGISTER");
        
        // Makes the call to using RMI
        Runnable registerThread1 = () -> {
                try {
                    authObject.register();
                } catch (NonUniqueDetailsExeception ex) {
                    System.out.println("Username/IC already exists");
                } catch (RemoteException ex) {
                    System.out.println("RemoteException");
                }
        };
        
        // Uses socket to feed and retrieve data
        Runnable registerThread2 = () -> {
            User newUser = new User("test0", "abc123", "aakif", "ahamath", (int)(Math.random()*10000));
            boolean success = false;
            try {  
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(newUser);
                oos.flush();
                
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                success = dis.readBoolean();
                oos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
                System.out.println(ex.getCause() + ex.getMessage());
            } finally {
                System.out.println("Register successful: " + success);
            }
        };
        
        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService register = Executors.newScheduledThreadPool(2);
        register.submit(registerThread1);
        register.schedule(registerThread2, 500, TimeUnit.MILLISECONDS);
        register.shutdown();
        register.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        
        /**
         * Sample Login
         */
        System.out.println("\n\tLOGIN");
        
        // Makes the call to using RMI
        Runnable loginThread1 = () -> {
            try {
                authObject.login();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        // Uses socket to feed and retrieve data
        Runnable loginThread2 = () -> {
            boolean success = false;
            String username = "test0";
            String password = "abc123";
            try {
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(username);
                dos.writeUTF(password);
                dos.flush();
                
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                success = dis.readBoolean();
                dos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            } finally {
                System.out.println("Login successful: " + success);
            }
        };

        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService login = Executors.newScheduledThreadPool(2);
        login.submit(loginThread1);
        login.schedule(loginThread2, 500, TimeUnit.MILLISECONDS);
        login.shutdown();
        login.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        
        /**
         * Sample Create Order
         */
        System.out.println("\n\tCREATE ORDER");
        
        
        // Makes the call using RMI
        Runnable createOrderThread1 = () -> {
            try {
                orderObject.createOrder();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        // Uses socket to feed and retrieve data
        Runnable createOrderThread2 = () -> {
            Order newOrder = new Order(1, "spacy", 1);
            boolean success = false;
            try {
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
                ois.writeObject(newOrder);
                ois.flush();
                
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                success = dis.readBoolean();
                ois.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            } finally {
                System.out.println("Order successful: " + success);
            }
        };
        
        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService createOrder = Executors.newScheduledThreadPool(2);
        createOrder.submit(createOrderThread1);
        createOrder.schedule(createOrderThread2, 500, TimeUnit.MILLISECONDS);
        createOrder.shutdown();
        createOrder.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        /**
         * Sample Get Orders
         */
        System.out.println("\n\tGET ORDERS");
        
        // Makes the call using RMI
        Runnable getOrdersThread1 = () -> {
            try {
                orderObject.getOrders();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        // Uses socket to feed and retrieve data
        Runnable getOrdersThread2 = () -> {
            LinkedList<Order> allOrders = new LinkedList<Order>();
            try {
                String username = "spacy";
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(username);
                dos.flush();
                
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                allOrders = (LinkedList<Order>)ois.readObject();
                dos.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException");
            } finally {
                if (!allOrders.isEmpty()) {
                    for (Order nextOrder : allOrders) {
                        System.out.println("Order ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity()
                                + "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
                    }
                } else {
                    System.out.println("No orders found!");
                }
            }
        };
        
        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService getOrders = Executors.newScheduledThreadPool(2);
        getOrders.submit(getOrdersThread1);
        getOrders.schedule(getOrdersThread2, 500, TimeUnit.MILLISECONDS);
        getOrders.shutdown();
        getOrders.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        
        
        /**
         * Sample Get Product
         */
        System.out.println("\n\tGET SINGLE PRODUCT");
        
        // Makes the call using RMI
        Runnable getProductThread1 = () -> {
            try {
                productObject.getProduct();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };

        // Uses socket to feed and retrieve data
        Runnable getProductThread2 = () -> {
            Integer productId = 3;
            Product fetchedProduct = null;
            try {
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(productId);
                dos.flush();
                
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                fetchedProduct = (Product)ois.readObject();
                dos.close();
                socket.close();
            } catch (IOException ex ) {
                System.out.println("IOException");
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException");
            }finally {
                try {
                    System.out.println("Product ID: " + fetchedProduct.getProductId() + "\nProduct Name: " + fetchedProduct.getProductName()
                            + "\nPrice: " + fetchedProduct.getPrice() + "\nTotal Supply: " + fetchedProduct.getTotalSupply());
                } catch (NullPointerException ex) {
                    System.out.println("Product not found!");
                }
            }
        };
        
        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService getProduct = Executors.newScheduledThreadPool(2);
        getProduct.submit(getProductThread1);
        getProduct.schedule(getProductThread2, 500, TimeUnit.MILLISECONDS);
        getProduct.shutdown();
        getProduct.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        /**
         * Sample Get Products
         */
        System.out.println("\n\tGET ALL PRODUCTS");
        
        // Makes the call using RMI
        Runnable getAllProductsThread1 = () -> {
            try {
                productObject.getAllProducts();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        // Uses socket to feed and retrieve data
        Runnable getAllProductsThread2 = () -> {
            LinkedList<Product> allProducts = new LinkedList<Product>();
            try {
                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
                
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                allProducts = (LinkedList<Product>)ois.readObject();
                ois.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException");
            } finally {
                if (!allProducts.isEmpty()) {
                    for (Product nextProduct : allProducts) {
                        System.out.println("Product ID: " + nextProduct.getProductId() + "\nProduct Name: " + nextProduct.getProductName()
                                + "\nPrice: " + nextProduct.getPrice() + "\nTotal Supply: " + nextProduct.getTotalSupply() + "\n");
                    }
                } else {
                    System.out.println("No products found!");
                }
            }
        };
        
        // Thread pool to ensure correct timing and completion of process before continuation
        ScheduledExecutorService getAllProducts = Executors.newScheduledThreadPool(2);
        getAllProducts.submit(getAllProductsThread1);
        getAllProducts.schedule(getAllProductsThread2, 500, TimeUnit.MILLISECONDS);
        getAllProducts.shutdown();
        getAllProducts.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
    }
    
}
