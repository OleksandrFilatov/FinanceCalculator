package com.alex.financetracker.entity;


public class MonthlyReport {

    private int year;
    private int month;
    private double totalIncome;
    private double totalCardExpense;
    private double totalCashExpense;
    private double totalExpense;
    private double balance;
    private double cumulativeBalance;

    public MonthlyReport(int year, int month, double totalIncome, double totalCardExpense,
                         double totalCashExpense, double totalExpense, double balance,
                         double cumulativeBalance) {
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalCardExpense = totalCardExpense;
        this.totalCashExpense = totalCashExpense;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.cumulativeBalance = cumulativeBalance;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "year=" + year +
                ", month=" + month +
                ", totalIncome=" + totalIncome +
                ", totalCardExpense=" + totalCardExpense +
                ", totalCashExpense=" + totalCashExpense +
                ", totalExpense=" + totalExpense +
                ", balance=" + balance +
                ", cumulativeBalance=" + cumulativeBalance +
                '}';
    }
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalCardExpense() {
        return totalCardExpense;
    }

    public double getTotalCashExpense() {
        return totalCashExpense;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public double getCumulativeBalance() {
        return cumulativeBalance;
    }
}
