package com.alex.financetracker.entity;

public class Expense {

    private int id;
    private int year;
    private int month;
    private double amount;
    private ExpenseType expenseType;
    private String category;
    private String description;

    // without ID
    public Expense(int year, int month, double amount, ExpenseType expenseType, String category, String description) {
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.expenseType = expenseType;
        this.category = category;
        this.description = description;
    }

    // with ID
    public Expense(int id, int year, int month, double amount, ExpenseType expenseType, String category, String description) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.expenseType = expenseType;
        this.category = category;
        this.description = description;
    }

    // getters
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

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    // setters
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

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", amount=" + amount +
                ", type=" + expenseType +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
