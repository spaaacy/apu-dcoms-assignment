/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mycompany.dcoms.assignment.order.Order;
import com.mycompany.dcoms.assignment.order.OrderInterface;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public class Client {
    
    static final String AUTH_SERVER_NAME = "auth";
    static final String ORDER_SERVER_NAME = "order";
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        
        AuthInterface auth = (AuthInterface)Naming.lookup("rmi://localhost:1050/" + AUTH_SERVER_NAME);
        OrderInterface order = (OrderInterface)Naming.lookup("rmi://localhost:1050/" + ORDER_SERVER_NAME);
           
        /**
         * Sample Register
         */
//        boolean success = false;
//        try {
//            User newUser = new User("spaaaacy", "abc123", "aakif", "ahamath", 123456);
//            success = auth.register(newUser);
//        } catch (UsernameExistsException ex) {
//            System.out.println("User already exists.");
//        } finally {
//            System.out.println("Register successful: " + success);
//        }
        
        /**
         * Sample Login
         */
//        boolean success = auth.login("spaaaacy", "abc123");
//        System.out.println("Login successful: " + success);
      
        /**
         * Sample Create Order
         */
//        Order newOrder = new Order(22, "spaaaacy", 4);
//        boolean success = order.createOrder(newOrder);
//        System.out.println("Order successful: " + success);

        /**
         * Sample Get Orders
         */
//        LinkedList<Order> allOrders = order.getOrders("spaaaacy");
//        for (Order nextOrder: allOrders) {
//            System.out.println("Order Details:\nOrder ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity() + 
//                    "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
//        }
        
    }
    
}
