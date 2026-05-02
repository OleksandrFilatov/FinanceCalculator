package com.alex.financetracker.ui;

import com.alex.financetracker.service.FinanceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private JLabel totalIncomeValue;
    private JLabel totalExpensesValue;
    private JLabel currentBalanceValue;
    private JLabel topCategoryValue;

    private FinanceService financeService;

    public DashboardPanel() {
        financeService = new FinanceService();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createCardsPanel(), BorderLayout.CENTER);

        loadDashboardData();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(e -> loadDashboardData());

        panel.add(title, BorderLayout.WEST);
        panel.add(refreshButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createCardsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        totalIncomeValue = new JLabel("0.00");
        totalExpensesValue = new JLabel("0.00");
        currentBalanceValue = new JLabel("0.00");
        topCategoryValue = new JLabel("No data");

        panel.add(createCard("Total income", totalIncomeValue));
        panel.add(createCard("Total expenses", totalExpensesValue));
        panel.add(createCard("Current balance", currentBalanceValue));
        panel.add(createCard("Top expense category", topCategoryValue));

        return panel;
    }

    private JPanel createCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void loadDashboardData() {
        double totalIncome = financeService.calculateTotalIncome();
        double totalExpenses = financeService.calculateTotalExpenses();
        double currentBalance = financeService.calculateCurrentBalance();
        String topCategory = financeService.findTopExpenseCategory();

        totalIncomeValue.setText(String.format("%.2f", totalIncome));
        totalExpensesValue.setText(String.format("%.2f", totalExpenses));
        currentBalanceValue.setText(String.format("%.2f", currentBalance));
        topCategoryValue.setText(topCategory);
    }
}