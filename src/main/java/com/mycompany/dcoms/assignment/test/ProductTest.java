/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.test;

import com.mycompany.dcoms.assignment.product.Product;
import com.mycompany.dcoms.assignment.product.ProductInterface;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
public class ProductTest {
    
    static final String PRODUCT_SERVER_NAME = "product";
    static final Integer RMI_PORT = 1050;    
    static final Integer SOCKET_PORT = 1060;
    static final String HOST_ADDRESS = "localhost";
    static final String SERVER_ADDRESS = "rmi://" + HOST_ADDRESS + ":" + RMI_PORT;
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, InterruptedException {
        
        ProductInterface productObject = (ProductInterface)Naming.lookup(SERVER_ADDRESS + "/" + PRODUCT_SERVER_NAME);
        
//        System.out.println("\n\tGET PRODUCT TEST 5.1");
//        
//        Runnable getProductThread1 = () -> {
//            try {
//                productObject.getProduct();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//
//        Runnable getProductThread2 = () -> {
//            Integer productId = 3;
//            Product fetchedProduct = null;
//            try {
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeInt(productId);
//                dos.flush();
//                
//                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                fetchedProduct = (Product)ois.readObject();
//                dos.close();
//                socket.close();
//            } catch (IOException ex ) {
//                System.out.println("IOException");
//            } catch (ClassNotFoundException ex) {
//                System.out.println("ClassNotFoundException");
//            }finally {
//                try {
//                    System.out.println("Product ID: " + fetchedProduct.getProductId() + "\nProduct Name: " + fetchedProduct.getProductName()
//                            + "\nPrice: " + fetchedProduct.getPrice() + "\nTotal Supply: " + fetchedProduct.getTotalSupply());
//                } catch (NullPointerException ex) {
//                    System.out.println("Product not found!");
//                }
//            }
//        };
//        
//        ScheduledExecutorService getProduct = Executors.newScheduledThreadPool(2);
//        getProduct.submit(getProductThread1);
//        getProduct.schedule(getProductThread2, 500, TimeUnit.MILLISECONDS);
//        getProduct.shutdown();
//        getProduct.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        System.out.println("\n\tGET PRODUCT TEST 5.2");
        
        Runnable getProductThread1 = () -> {
            try {
                productObject.getProduct();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };

        Runnable getProductThread2 = () -> {
            Integer productId = 10;
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
        
        ScheduledExecutorService getProduct = Executors.newScheduledThreadPool(2);
        getProduct.submit(getProductThread1);
        getProduct.schedule(getProductThread2, 500, TimeUnit.MILLISECONDS);
        getProduct.shutdown();
        getProduct.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

//        System.out.println("\n\tGET ALL PRODUCTS TEST 6.1");
//        
//        Runnable getAllProductsThread1 = () -> {
//            try {
//                productObject.getAllProducts();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//        
//        Runnable getAllProductsThread2 = () -> {
//            LinkedList<Product> allProducts = new LinkedList<Product>();
//            try {
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                
//                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                allProducts = (LinkedList<Product>)ois.readObject();
//                ois.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } catch (ClassNotFoundException ex) {
//                System.out.println("ClassNotFoundException");
//            } finally {
//                if (!allProducts.isEmpty()) {
//                    for (Product nextProduct : allProducts) {
//                        System.out.println("Product ID: " + nextProduct.getProductId() + "\nProduct Name: " + nextProduct.getProductName()
//                                + "\nPrice: " + nextProduct.getPrice() + "\nTotal Supply: " + nextProduct.getTotalSupply() + "\n");
//                    }
//                } else {
//                    System.out.println("No products found!");
//                }
//            }
//        };
//        
//        ScheduledExecutorService getAllProducts = Executors.newScheduledThreadPool(2);
//        getAllProducts.submit(getAllProductsThread1);
//        getAllProducts.schedule(getAllProductsThread2, 500, TimeUnit.MILLISECONDS);
//        getAllProducts.shutdown();
//        getAllProducts.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        System.out.println("\n\tGET ALL PRODUCTS TEST 6.2");
        
        Runnable getAllProductsThread1 = () -> {
            try {
                productObject.getAllProducts();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
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
        
        ScheduledExecutorService getAllProducts = Executors.newScheduledThreadPool(2);
        getAllProducts.submit(getAllProductsThread1);
        getAllProducts.schedule(getAllProductsThread2, 500, TimeUnit.MILLISECONDS);
        getAllProducts.shutdown();
        getAllProducts.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
    }
    
}
