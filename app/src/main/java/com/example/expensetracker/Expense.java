package com.example.expensetracker;

import java.io.Serializable;

public class Expense implements Serializable{
    int id;
    String date;
    String time;
    String type;
    double montant;
    String address;

    public Expense() { }

    public Expense(int id, String date, String time, String type, double montant, String address) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.type = type;
        this.montant = montant;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
