package com.alex.financetracker.ui;

import com.alex.financetracker.entity.MonthlyReport;
import com.alex.financetracker.service.FinanceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReportPanel extends JPanel {

    private JComboBox<Integer> yearBox;
    private JComboBox<String> monthBox;

    private JLabel incomeValue;
    private JLabel cardExpenseValue;
    private JLabel cashExpenseValue;
    private JLabel totalExpenseValue;
    private JLabel balanceValue;

    private FinanceService financeService;

    public ReportPanel() {
        financeService = new FinanceService();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createFilterPanel(), BorderLayout.NORTH);
        add(createReportPanel(), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Monthly Report");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 3, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);

        yearBox = new JComboBox<>(new Integer[]{2024, 2025, 2026, 2027});

        monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        });

        JButton showButton = new JButton("Show report");
        showButton.setFocusPainted(false);
        showButton.setFont(new Font("Arial", Font.BOLD, 14));
        showButton.addActionListener(e -> showReport());

        fieldsPanel.add(new JLabel("Year"));
        fieldsPanel.add(new JLabel("Month"));
        fieldsPanel.add(new JLabel(""));

        fieldsPanel.add(yearBox);
        fieldsPanel.add(monthBox);
        fieldsPanel.add(showButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(fieldsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));
        panel.setBackground(Color.WHITE);

        incomeValue = new JLabel("0.00");
        cardExpenseValue = new JLabel("0.00");
        cashExpenseValue = new JLabel("0.00");
        totalExpenseValue = new JLabel("0.00");
        balanceValue = new JLabel("0.00");

        addReportRow(panel, "Total income:", incomeValue);
        addReportRow(panel, "Card expenses:", cardExpenseValue);
        addReportRow(panel, "Cash expenses:", cashExpenseValue);
        addReportRow(panel, "Total expenses:", totalExpenseValue);
        addReportRow(panel, "Balance:", balanceValue);

        return panel;
    }

    private void addReportRow(JPanel panel, String labelText, JLabel valueLabel) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(label);
        panel.add(valueLabel);
    }

    private void showReport() {
        int year = (Integer) yearBox.getSelectedItem();
        int month = monthBox.getSelectedIndex() + 1;

        MonthlyReport report = financeService.createMonthlyReport(year, month);

        incomeValue.setText(String.format("%.2f", report.getTotalIncome()));
        cardExpenseValue.setText(String.format("%.2f", report.getTotalCardExpense()));
        cashExpenseValue.setText(String.format("%.2f", report.getTotalCashExpense()));
        totalExpenseValue.setText(String.format("%.2f", report.getTotalExpense()));
        balanceValue.setText(String.format("%.2f", report.getBalance()));
    }
}