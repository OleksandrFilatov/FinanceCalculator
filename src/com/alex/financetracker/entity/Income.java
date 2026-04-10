package com.alex.financetracker.entity;

public class Income {
    private int id;
    private int year;
    private int month;
    private double amount;
    private String description;

    //with ID
    public Income(int year, int month, double amount, String description) {
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.description = description;
    }
    //without ID
    public Income(int id, int year, int month, double amount, String description) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.description = description;
    }
    //getters
    public int getId() {
        return id;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    // settets
    public void setId(int id) {
        this.id = id;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}