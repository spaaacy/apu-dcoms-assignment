/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.auth;

/**
 *
 * @author aakif
 */
public class User implements java.io.Serializable {
    
    String username;
    String password;
    String firstName;
    String lastName;
    Integer icNumber;

    public User(String username, String password, String firstName, String lastName, Integer icNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.icNumber = icNumber;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(Integer icNumber) {
        this.icNumber = icNumber;
    }
      
}
