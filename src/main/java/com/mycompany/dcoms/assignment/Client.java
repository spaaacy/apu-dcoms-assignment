/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

import com.mycompany.dcoms.assignment.auth.AuthInterface;
import com.mycompany.dcoms.assignment.auth.UsernameExistsException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import model.User;

/**
 *
 * @author aakif
 */
public class Client {
    
    static String authServerName = "Auth";
    
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        AuthInterface auth = (AuthInterface)Naming.lookup("rmi://localhost:1050/" + authServerName);
           
        // Register
        try {
            User newUser = new User("spacy", "abc123", "aakif", "ahamath", "123456");
            boolean success = auth.register(newUser);
            System.out.println("Register was successful.");
        } catch (UsernameExistsException ex) {
            System.out.println("Register was not successful.");
        }
        
        // Login
//        boolean success = auth.login("spacy1", "abc123");
//        System.out.println("Login was successful? " + success);
        
    }
    
}
