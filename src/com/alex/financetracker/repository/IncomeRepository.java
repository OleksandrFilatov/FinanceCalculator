package com.alex.financetracker.repository;

import com.alex.financetracker.config.DatabaseConfig;
import com.alex.financetracker.entity.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            throw new RuntimeException("Failed to save income", e);
        }
    }

    public List<Income> findAll() {

        List<Income> incomes = new ArrayList<>();

        String sql = "SELECT * FROM incomes ORDER BY year, month, id";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int year = resultSet.getInt("year");
                int month = resultSet.getInt("month");
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");

                Income income = new Income(id, year, month, amount, description);

                incomes.add(income);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load incomes", e);
        }

        return incomes;
    }

    public void deleteById(int id) {

        String sql = "DELETE FROM incomes WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Income deleted!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete income", e);
        }
    }

    public void update(Income income) {

        String sql = "UPDATE incomes SET year = ?, month = ?, amount = ?, description = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, income.getYear());
            statement.setInt(2, income.getMonth());
            statement.setDouble(3, income.getAmount());
            statement.setString(4, income.getDescription());
            statement.setInt(5, income.getId());

            statement.executeUpdate();

            System.out.println("Income updated!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to update income", e);
        }
    }

    public List<Income> searchByDescription(String keyword) {

        List<Income> incomes = new ArrayList<>();

        String sql = "SELECT * FROM incomes WHERE description LIKE ? ORDER BY year, month, id";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int year = resultSet.getInt("year");
                    int month = resultSet.getInt("month");
                    double amount = resultSet.getDouble("amount");
                    String description = resultSet.getString("description");

                    Income income = new Income(id, year, month, amount, description);

                    incomes.add(income);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to search incomes", e);
        }

        return incomes;
    }
}
