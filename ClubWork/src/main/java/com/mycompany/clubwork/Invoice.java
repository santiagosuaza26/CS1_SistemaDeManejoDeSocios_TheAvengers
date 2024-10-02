/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clubwork;

/**
 *
 * @author ssuaz
 */
public class Invoice {
    private String concept;
    private double amount;
    private String memberName;

    // Constructor
    public Invoice(String concept, double amount, String memberName) {
        this.concept = concept;
        this.amount = amount;
        this.memberName = memberName;
    }

    // Getters
    public String getConcept() {
        return concept;
    }

    public double getAmount() {
        return amount;
    }

    public String getMemberName() {
        return memberName;
    }
}

