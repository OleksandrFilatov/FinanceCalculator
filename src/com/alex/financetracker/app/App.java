package com.alex.financetracker.app;

import com.alex.financetracker.config.DatabaseConfig;
import java.sql.Connection;

import com.alex.financetracker.config.DatabaseInitializer;
import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;
import com.alex.financetracker.entity.Income;
import com.alex.financetracker.repository.IncomeRepository;


public class App {
    public static void main(String[] args) {
            DatabaseInitializer.initialize();

        IncomeRepository repo = new IncomeRepository();

        Income income = new Income(2025, 6, 300.0, "Salary");

        repo.save(income);
        }
    }
