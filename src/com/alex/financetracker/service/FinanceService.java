package com.alex.financetracker.service;

import com.alex.financetracker.config.AppSettings;
import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;
import com.alex.financetracker.entity.Income;
import com.alex.financetracker.entity.MonthlyReport;
import com.alex.financetracker.repository.ExpenseRepository;
import com.alex.financetracker.repository.IncomeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceService {

    private IncomeRepository incomeRepository;
    private ExpenseRepository expenseRepository;

    public FinanceService() {
        this.incomeRepository = new IncomeRepository();
        this.expenseRepository = new ExpenseRepository();
    }

    public MonthlyReport createMonthlyReport(int year, int month) {

        List<Income> incomes = incomeRepository.findAll();
        List<Expense> expenses = expenseRepository.findAll();

        double totalIncome = 0;
        double totalCardExpense = 0;
        double totalCashExpense = 0;

        for (Income income : incomes) {
            if (income.getYear() == year && income.getMonth() == month) {
                totalIncome += income.getAmount();
            }
        }

        for (Expense expense : expenses) {
            if (expense.getYear() == year && expense.getMonth() == month) {
                if (expense.getExpenseType() == ExpenseType.CARD) {
                    totalCardExpense += expense.getAmount();
                } else if (expense.getExpenseType() == ExpenseType.CASH) {
                    totalCashExpense += expense.getAmount();
                }
            }
        }

        double totalExpense = totalCardExpense + totalCashExpense;
        double balance = totalIncome - totalExpense;

        return new MonthlyReport(
                year,
                month,
                totalIncome,
                totalCardExpense,
                totalCashExpense,
                totalExpense,
                balance,
                balance
        );
    }

    public List<MonthlyReport> createAllMonthlyReports() {

        List<Income> incomes = incomeRepository.findAll();
        List<Expense> expenses = expenseRepository.findAll();

        List<MonthlyReport> reports = new ArrayList<>();

        double cumulativeBalance = 0;

        for (int year = AppSettings.START_YEAR; year <= AppSettings.END_YEAR; year++) {
            for (int month = 1; month <= 12; month++) {

                double totalIncome = 0;
                double totalCardExpense = 0;
                double totalCashExpense = 0;

                for (Income income : incomes) {
                    if (income.getYear() == year && income.getMonth() == month) {
                        totalIncome += income.getAmount();
                    }
                }

                for (Expense expense : expenses) {
                    if (expense.getYear() == year && expense.getMonth() == month) {
                        if (expense.getExpenseType() == ExpenseType.CARD) {
                            totalCardExpense += expense.getAmount();
                        } else if (expense.getExpenseType() == ExpenseType.CASH) {
                            totalCashExpense += expense.getAmount();
                        }
                    }
                }

                double totalExpense = totalCardExpense + totalCashExpense;
                double balance = totalIncome - totalExpense;

                if (totalIncome > 0 || totalExpense > 0) {
                    cumulativeBalance += balance;

                    MonthlyReport report = new MonthlyReport(
                            year,
                            month,
                            totalIncome,
                            totalCardExpense,
                            totalCashExpense,
                            totalExpense,
                            balance,
                            cumulativeBalance
                    );

                    reports.add(report);
                }
            }
        }

        return reports;
    }

    public List<MonthlyReport> createReportsByYear(int selectedYear) {

        List<MonthlyReport> allReports = createAllMonthlyReports();
        List<MonthlyReport> filteredReports = new ArrayList<>();

        for (MonthlyReport report : allReports) {
            if (report.getYear() == selectedYear) {
                filteredReports.add(report);
            }
        }

        return filteredReports;
    }
    public Map<String, Double> calculateExpensesByCategory() {

        List<Expense> expenses = expenseRepository.findAll();

        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();

            if (categoryTotals.containsKey(category)) {
                double currentTotal = categoryTotals.get(category);
                categoryTotals.put(category, currentTotal + amount);
            } else {
                categoryTotals.put(category, amount);
            }
        }

        return categoryTotals;
    }
    public double calculateTotalIncome() {
        List<Income> incomes = incomeRepository.findAll();

        double total = 0;

        for (Income income : incomes) {
            total += income.getAmount();
        }

        return total;
    }

    public double calculateTotalExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public double calculateCurrentBalance() {
        return calculateTotalIncome() - calculateTotalExpenses();
    }

    public String findTopExpenseCategory() {
        Map<String, Double> categoryTotals = calculateExpensesByCategory();

        String topCategory = "No data";
        double maxAmount = 0;

        for (String category : categoryTotals.keySet()) {
            double amount = categoryTotals.get(category);

            if (amount > maxAmount) {
                maxAmount = amount;
                topCategory = category;
            }
        }

        return topCategory;
    }
}
