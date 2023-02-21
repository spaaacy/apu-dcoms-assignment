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
    
    public boolean createOrder(int product_id, String username, int quantity) throws RemoteException;
    public void getOrders(String username) throws RemoteException;
    
}
