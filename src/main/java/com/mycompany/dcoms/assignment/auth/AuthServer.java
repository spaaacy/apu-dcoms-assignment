/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.auth;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author aakif
 */
public class AuthServer {
    
    static final String AUTH_SERVER_NAME = "auth";
    
    public static void main(String[] args) throws RemoteException {
        AuthInterface authService = new AuthObject();
        Registry reg = LocateRegistry.createRegistry(1050);
        reg.rebind(AUTH_SERVER_NAME, authService);
    }
}
