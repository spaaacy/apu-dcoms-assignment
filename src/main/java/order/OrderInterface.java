/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 *
 * @author aakif
 */
public interface OrderInterface extends Remote {
    
    public boolean createOrder() throws RemoteException;
    public LinkedList<Product> getOrders(String username) throws RemoteException;
    
}
