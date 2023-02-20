/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment;

/**
 *
 * @author aakif
 */
public class UsernameExistsException extends Exception {
    
    static String errorMessage = "Username already exists!";
    
    UsernameExistsException() {
        super(errorMessage);
    }
    
}
