/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.test;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.NonUniqueDetailsExeception;
import com.mycompany.dcoms.assignment.auth.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author aakif
 */
public class AuthTest {
    
    static final String AUTH_SERVER_NAME = "auth";
    static final Integer RMI_PORT = 1050;    
    static final Integer SOCKET_PORT = 1060;
    static final String HOST_ADDRESS = "localhost";
    static final String SERVER_ADDRESS = "rmi://" + HOST_ADDRESS + ":" + RMI_PORT;
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException, InterruptedException {
        
        AuthInterface authObject = (AuthInterface)Naming.lookup(SERVER_ADDRESS + "/" + AUTH_SERVER_NAME);
        
//        System.out.println("\tREGISTER TEST 1.1");
//        
//        Runnable registerThread1 = () -> {
//                try {
//                    authObject.register();
//                } catch (NonUniqueDetailsExeception ex) {
//                    System.out.println("Username/IC already exists");
//                } catch (RemoteException ex) {
//                    System.out.println("RemoteException");
//                }
//        };
//        
//        Runnable registerThread2 = () -> {
//            User newUser = new User("test", "abc123", "aakif", "ahamath", "123456");
//            boolean success = false;
//            try {  
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                oos.writeObject(newUser);
//                oos.flush();
//                
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                success = dis.readBoolean();
//                oos.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } finally {
//                System.out.println("Register successful: " + success);
//            }
//        };
//        
//        ScheduledExecutorService register = Executors.newScheduledThreadPool(2);
//        register.submit(registerThread1);
//        register.schedule(registerThread2, 500, TimeUnit.MILLISECONDS);
//        register.shutdown();
//        register.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

//        System.out.println("\tREGISTER TEST 1.2");
//        
//        Runnable registerThread1 = () -> {
//                try {
//                    authObject.register();
//                } catch (NonUniqueDetailsExeception ex) {
//                    System.out.println("Username/IC already exists");
//                } catch (RemoteException ex) {
//                    System.out.println("RemoteException");
//                }
//        };
//        
//        Runnable registerThread2 = () -> {
//            User newUser = new User("test", "abc123", "aakif", "ahamath", "123456");
//            boolean success = false;
//            try {  
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                oos.writeObject(newUser);
//                oos.flush();
//                
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                success = dis.readBoolean();
//                oos.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } finally {
//                System.out.println("Register successful: " + success);
//            }
//        };
//        
//        ScheduledExecutorService register = Executors.newScheduledThreadPool(2);
//        register.submit(registerThread1);
//        register.schedule(registerThread2, 500, TimeUnit.MILLISECONDS);
//        register.shutdown();
//        register.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
//        System.out.println("\n\tLOGIN TEST 2.1");
//        
//        Runnable loginThread1 = () -> {
//            try {
//                authObject.login();
//            } catch (RemoteException ex) {
//                System.out.println("RemoteException");
//            }
//        };
//        
//        Runnable loginThread2 = () -> {
//            boolean success = false;
//            String username = "test";
//            String password = "abc123";
//            try {
//                Socket socket = new Socket(HOST_ADDRESS, SOCKET_PORT);
//                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                dos.writeUTF(username);
//                dos.writeUTF(password);
//                dos.flush();
//                
//                DataInputStream dis = new DataInputStream(socket.getInputStream());
//                success = dis.readBoolean();
//                dos.close();
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("IOException");
//            } finally {
//                System.out.println("Login successful: " + success);
//            }
//        };
//
//        ScheduledExecutorService login = Executors.newScheduledThreadPool(2);
//        login.submit(loginThread1);
//        login.schedule(loginThread2, 500, TimeUnit.MILLISECONDS);
//        login.shutdown();
//        login.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        System.out.println("\n\tLOGIN TEST 2.2");
        
        Runnable loginThread1 = () -> {
            try {
                authObject.login();
            } catch (RemoteException ex) {
                System.out.println("RemoteException");
            }
        };
        
        Runnable loginThread2 = () -> {
            boolean success = false;
            String username = "admin";
            String password = "password";
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

        ScheduledExecutorService login = Executors.newScheduledThreadPool(2);
        login.submit(loginThread1);
        login.schedule(loginThread2, 500, TimeUnit.MILLISECONDS);
        login.shutdown();
        login.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
    }
    
}
