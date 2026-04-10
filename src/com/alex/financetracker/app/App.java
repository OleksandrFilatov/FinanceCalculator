package com.alex.financetracker.app;

import com.alex.financetracker.config.DatabaseConfig;
import java.sql.Connection;
import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;
import com.alex.financetracker.entity.Income;


public class App {
    public static void main(String[] args) {

        try (Connection connection = DatabaseConfig.getConnection()) {
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
