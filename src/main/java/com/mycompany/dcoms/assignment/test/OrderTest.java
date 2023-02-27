/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.test;

import com.mycompany.dcoms.assignment.order.Order;
import com.mycompany.dcoms.assignment.order.OrderInterface;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author aakif
 */
public class OrderTest {
    
    static final String ORDER_SERVER_NAME = "order";
    static final Integer RMI_PORT = 1050;    
    static final Integer SOCKET_PORT = 1060;
    static final String HOST_ADDRESS = "localhost";
    static final String SERVER_ADDRESS = "rmi://" + HOST_ADDRESS + ":" + RMI_PORT;
    
    public static void main(String[] args) throws InterruptedException, NotBoundException, MalformedURLException, RemoteException{
        
        OrderInterface orderObject = (OrderInterface)Naming.lookup(SERVER_ADDRESS + "/" + ORDER_SERVER_NAME);
        
//        System.out.println("\n\tCREATE ORDER TEST 3.1");
//
//        Runnable createOrderThread1 = () -> {
//            try {
//                orderObject.createOrder();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//        
//        Runnable createOrderThread2 = () -> {
//            Order newOrder = new Order(3, "test", 5);
//            boolean success = false;
//            try {
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
//                ois.writeObject(newOrder);
//                ois.flush();
//                
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                success = dis.readBoolean();
//                ois.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } finally {
//                System.out.println("Order successful: " + success);
//            }
//        };
//        
//        ScheduledExecutorService createOrder = Executors.newScheduledThreadPool(2);
//        createOrder.submit(createOrderThread1);
//        createOrder.schedule(createOrderThread2, 500, TimeUnit.MILLISECONDS);
//        createOrder.shutdown();
//        createOrder.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

//        System.out.println("\n\tCREATE ORDER TEST 3.2");
//
//        Runnable createOrderThread1 = () -> {
//            try {
//                orderObject.createOrder();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//        
//        Runnable createOrderThread2 = () -> {
//            Order newOrder = new Order(10, "admin", 1);
//            boolean success = false;
//            try {
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
//                ois.writeObject(newOrder);
//                ois.flush();
//                
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                success = dis.readBoolean();
//                ois.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } finally {
//                System.out.println("Order successful: " + success);
//            }
//        };
//        
//        ScheduledExecutorService createOrder = Executors.newScheduledThreadPool(2);
//        createOrder.submit(createOrderThread1);
//        createOrder.schedule(createOrderThread2, 500, TimeUnit.MILLISECONDS);
//        createOrder.shutdown();
//        createOrder.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
//        System.out.println("\n\tGET ORDERS TEST 4.1");
//        
//        Runnable getOrdersThread1 = () -> {
//            try {
//                orderObject.getOrders();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//        
//        Runnable getOrdersThread2 = () -> {
//            LinkedList<Order> allOrders = new LinkedList<Order>();
//            try {
//                String username = "test";
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeUTF(username);
//                dos.flush();
//                
//                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                allOrders = (LinkedList<Order>)ois.readObject();
//                dos.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } catch (ClassNotFoundException ex) {
//                System.out.println("ClassNotFoundException");
//            } finally {
//                if (!allOrders.isEmpty()) {
//                    for (Order nextOrder : allOrders) {
//                        System.out.println("Order ID: " + nextOrder.getOrderId() + "\nQuantity: " + nextOrder.getQuantity()
//                                + "\nUsername: " + nextOrder.getUsername() + "\nProduct ID: " + nextOrder.getProductId() + "\n");
//                    }
//                } else {
//                    System.out.println("No orders found!");
//                }
//            }
//        };
//        
//        ScheduledExecutorService getOrders = Executors.newScheduledThreadPool(2);
//        getOrders.submit(getOrdersThread1);
//        getOrders.schedule(getOrdersThread2, 500, TimeUnit.MILLISECONDS);
//        getOrders.shutdown();
//        getOrders.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

System.out.println("\n\tGET ORDERS TEST 4.2");
        
        Runnable getOrdersThread1 = () -> {
            try {
                orderObject.getOrders();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        Runnable getOrdersThread2 = () -> {
            LinkedList<Order> allOrders = new LinkedList<Order>();
            try {
                String username = "test";
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
        
        ScheduledExecutorService getOrders = Executors.newScheduledThreadPool(2);
        getOrders.submit(getOrdersThread1);
        getOrders.schedule(getOrdersThread2, 500, TimeUnit.MILLISECONDS);
        getOrders.shutdown();
        getOrders.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
    }
    
}
