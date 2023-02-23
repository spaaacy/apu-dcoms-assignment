/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public interface ProductInterface extends Remote {
    
    public LinkedList<Product> getAllProducts() throws RemoteException;
    public Product getProduct(Integer productId) throws RemoteException;
    
}
