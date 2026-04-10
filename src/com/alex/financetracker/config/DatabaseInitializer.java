package com.alex.financetracker.config;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {


    public static void initialize() {
        String createIncomesTable = """
                CREATE TABLE IF NOT EXISTS incomes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    year INTEGER NOT NULL,
                    month INTEGER NOT NULL,
                    amount REAL NOT NULL,
                    description TEXT
                );
                """;

        String createExpensesTable = """
                CREATE TABLE IF NOT EXISTS expenses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    year INTEGER NOT NULL,
                    month INTEGER NOT NULL,
                    amount REAL NOT NULL,
                    expense_type TEXT NOT NULL,
                    category TEXT,
                    description TEXT
                );
                """;
        try (Connection connection = DatabaseConfig.getConnection()) {
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createIncomesTable);
            statement.execute(createExpensesTable);

            System.out.println("Tables created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
