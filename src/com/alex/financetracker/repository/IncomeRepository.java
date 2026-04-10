package com.alex.financetracker.repository;

import com.alex.financetracker.config.DatabaseConfig;
import com.alex.financetracker.entity.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class IncomeRepository {

    public void save(Income income) {

        String sql = "INSERT INTO incomes(year, month, amount, description) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, income.getYear());
            statement.setInt(2, income.getMonth());
            statement.setDouble(3, income.getAmount());
            statement.setString(4, income.getDescription());

            statement.executeUpdate();

            System.out.println("Income saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}