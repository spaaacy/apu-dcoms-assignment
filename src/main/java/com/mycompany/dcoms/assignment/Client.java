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

/**
 *
 * @author aakif
 */
public class Client {
    
    static String authServerName = "AuthServer";
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        AuthInterface auth = (AuthInterface)Naming.lookup("rmi://localhost:1050/" + authServerName);
           
        // Register
//        try {
//            boolean success = auth.register("spacy5", "abc123", "aakif", "ahamath");
//            System.out.println("Register was successful.");
//        } catch (UsernameExistsException ex) {
//            System.out.println("Register was not successful.");
//        }
        
        // Login
        boolean success = auth.login("spacy1", "abc123");
        System.out.println("Login was successful? " + success);
        
    }
    
}
