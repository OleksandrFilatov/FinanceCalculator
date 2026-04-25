package com.alex.financetracker.repository;

import com.alex.financetracker.config.DatabaseConfig;
import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    public void save(Expense expense) {

        String sql = "INSERT INTO expenses(year, month, amount, expense_type, category, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, expense.getYear());
            statement.setInt(2, expense.getMonth());
            statement.setDouble(3, expense.getAmount());
            statement.setString(4, expense.getExpenseType().name());
            statement.setString(5, expense.getCategory());
            statement.setString(6, expense.getDescription());

            statement.executeUpdate();

            System.out.println("Expense saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Expense> findAll() {

        List<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int year = resultSet.getInt("year");
                int month = resultSet.getInt("month");
                double amount = resultSet.getDouble("amount");

                String typeFromDb = resultSet.getString("expense_type");
                ExpenseType expenseType = ExpenseType.valueOf(typeFromDb);

                String category = resultSet.getString("category");
                String description = resultSet.getString("description");

                Expense expense = new Expense(id, year, month, amount, expenseType, category, description);

                expenses.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenses;
    }
}