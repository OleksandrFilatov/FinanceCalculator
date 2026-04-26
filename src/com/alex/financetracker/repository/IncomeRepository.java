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
            e.printStackTrace();
        }
    }

    public List<Income> findAll() {

        List<Income> incomes = new ArrayList<>();

        String sql = "SELECT * FROM incomes";

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public List<Income> searchByDescription(String keyword) {

        List<Income> incomes = new ArrayList<>();

        String sql = "SELECT * FROM incomes WHERE description LIKE ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

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
            e.printStackTrace();
        }

        return incomes;
    }
}