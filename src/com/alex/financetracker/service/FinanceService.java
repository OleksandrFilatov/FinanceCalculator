package com.alex.financetracker.service;

import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;
import com.alex.financetracker.entity.Income;
import com.alex.financetracker.entity.MonthlyReport;
import com.alex.financetracker.repository.ExpenseRepository;
import com.alex.financetracker.repository.IncomeRepository;

import java.util.List;

public class FinanceService {

    private IncomeRepository incomeRepository;
    private ExpenseRepository expenseRepository;

    public FinanceService() {
        incomeRepository = new IncomeRepository();
        expenseRepository = new ExpenseRepository();
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
}
