/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

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
//        auth.register("aakif", "ahamath", "spacy1", "abc123");
        try {
            boolean success = auth.login("spacy", "abc123");
            System.out.println("Login was successful? " + success);
        } catch (MultipleUserException ex) {}
    }
    
}
