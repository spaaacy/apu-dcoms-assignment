/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.UsernameExistsException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.mycompany.dcoms.assignment.auth.User;

/**
 *
 * @author aakif
 */
public class Client {
    
    static String authServerName = "auth";
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        AuthInterface auth = (AuthInterface)Naming.lookup("rmi://localhost:1050/" + authServerName);
           
        /**
         * Register
         */
        boolean success = false;
        try {
            User newUser = new User("spaaaacy2", "abc123", "aakif", "ahamath", 123456);
            success = auth.register(newUser);
        } catch (UsernameExistsException ex) {
            System.out.println("User already exists.");
        } finally {
            System.out.println("Register successful: " + success);
        }
        
        /**
         * Login
         */
//        boolean success = auth.login("spaaaacy", "abc123");
//        System.out.println("Login successful: " + success);
        
    }
    
}
