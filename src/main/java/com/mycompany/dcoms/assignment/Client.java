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

        AuthInterface authObject = (AuthInterface) Naming.lookup(SERVER_ADDRESS + "/" + AUTH_SERVER_NAME);
        OrderInterface orderObject = (OrderInterface) Naming.lookup(SERVER_ADDRESS + "/" + ORDER_SERVER_NAME);
        ProductInterface productObject = (ProductInterface) Naming.lookup(SERVER_ADDRESS + "/" + PRODUCT_SERVER_NAME);

        /**
         * Sample Register
         */
        System.out.println("\tREGISTER");

        boolean successRegister = false;
        try {
            
            User newUser = new User("test04", "abc123", "aakif", "ahamath", "002142");
            authObject.register();
            Thread.sleep(500L);
            
            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(newUser);
            oos.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            successRegister = dis.readBoolean();
            oos.close();
            socket.close();
            
        } catch (NonUniqueDetailsExeception ex) {
            System.out.println("Username/IC already exists");
        } catch (RemoteException ex) {
            System.out.println("RemoteException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } 
        
        finally {
            System.out.println("Register successful: " + successRegister);
        }

        /**
         * Sample Login
         */
        System.out.println("\n\tLOGIN");

        boolean successLogin = false;
        String usernameLogin = "test03";
        String password = "abc123";
        try {
            
            authObject.login();
            Thread.sleep(500L);
            
            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(usernameLogin);
            dos.writeUTF(password);
            dos.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            successLogin = dis.readBoolean();
            dos.close();
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("IOException");
        } 
        
        finally {
            System.out.println("Login successful: " + successLogin);
        }

        /**
         * Sample Create Order
         */
        System.out.println("\n\tCREATE ORDER");

        boolean successCreateOrder = false;
        try {
            
            Order newOrder = new Order(5, "spacy", 1);
            orderObject.createOrder();
            Thread.sleep(500L);
            
            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
            ois.writeObject(newOrder);
            ois.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            successCreateOrder = dis.readBoolean();
            ois.close();
            socket.close();
            
        } catch (RemoteException ex) {
            System.out.println("RemoteException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } 
        
        finally {
            System.out.println("Order successful: " + successCreateOrder);
        }

        /**
         * Sample Get Orders
         */
        System.out.println("\n\tGET ORDERS");

        LinkedList<Order> allOrders = new LinkedList<Order>();
        try {
            
            orderObject.getOrders();
            Thread.sleep(500L);
            
            String username = "spacy";
            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(username);
            dos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            allOrders = (LinkedList<Order>) ois.readObject();
            dos.close();
            socket.close();
            
        } catch (RemoteException ex) {
            System.out.println("RemoteException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } 
        
        finally {
            if (!allOrders.isEmpty()) {
                for (Order nextOrder : allOrders) {
                    System.out.println("Order ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity()
                            + "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
                }
            } else {
                System.out.println("No orders found!");
            }
        }

        /**
         * Sample Get Product
         */
        System.out.println("\n\tGET SINGLE PRODUCT");

        Product fetchedProduct = null;
        try {
            
            Integer productId = 3;
            productObject.getProduct();
            Thread.sleep(500L);

            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(productId);
            dos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            fetchedProduct = (Product) ois.readObject();
            dos.close();
            socket.close();
            
        } catch (RemoteException ex) {
            System.out.println("RemoteException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } 
        
        finally {
            try {
                System.out.println("Product ID: " + fetchedProduct.getProductId() + "\nProduct Name: " + fetchedProduct.getProductName()
                        + "\nPrice: " + fetchedProduct.getPrice() + "\nTotal Supply: " + fetchedProduct.getTotalSupply());
            } catch (NullPointerException ex) {
                System.out.println("Product not found!");
            }
        }

        /**
         * Sample Get Products
         */
        System.out.println("\n\tGET ALL PRODUCTS");

        LinkedList<Product> allProducts = new LinkedList<Product>();
        try {
            
            productObject.getAllProducts();
            Thread.sleep(500L);

            Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            allProducts = (LinkedList<Product>) ois.readObject();
            ois.close();
            socket.close();
            
        } catch (RemoteException ex) {
            System.out.println("RemoteException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } 
        
        finally {
            if (!allProducts.isEmpty()) {
                for (Product nextProduct : allProducts) {
                    System.out.println("Product ID: " + nextProduct.getProductId() + "\nProduct Name: " + nextProduct.getProductName()
                            + "\nPrice: " + nextProduct.getPrice() + "\nTotal Supply: " + nextProduct.getTotalSupply() + "\n");
                }
            } else {
                System.out.println("No products found!");
            }
        }

    }

}
