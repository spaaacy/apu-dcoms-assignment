/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dcoms.assignment.product;

/**
 *
 * @author aakif
 */
public class Product implements java.io.Serializable {
    Integer productId;
    String productName;
    Double price;
    Integer totalSupply;

    public Product(Integer productId, String productName, Double price, Integer totalSupply) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.totalSupply = totalSupply;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Integer totalSupply) {
        this.totalSupply = totalSupply;
    }
    
    
    
}
