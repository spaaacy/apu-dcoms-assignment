/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.auth;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author aakif
 */
public interface AuthInterface extends Remote {

    public void register() throws RemoteException;
    public void login() throws RemoteException;

}
