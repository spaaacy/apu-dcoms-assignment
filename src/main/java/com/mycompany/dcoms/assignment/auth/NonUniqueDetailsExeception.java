/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.auth;

/**
 *
 * @author aakif
 */
public class NonUniqueDetailsExeception extends Exception {
    
    static final String SQL_PRIMARY_KEY_ERROR_CODE = "23505";
    static String errorMessage = "Username already exists!";
    
    NonUniqueDetailsExeception(Exception ex) {
        super(errorMessage, ex);
    }
    
}
