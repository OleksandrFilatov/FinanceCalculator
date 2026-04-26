package com.alex.financetracker.ui;

import com.alex.financetracker.entity.MonthlyReport;
import com.alex.financetracker.service.FinanceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel {

    private JTable reportTable;
    private DefaultTableModel tableModel;

    private FinanceService financeService;

    public ReportPanel() {
        financeService = new FinanceService();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadReports();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Monthly Reports");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton refreshButton = new JButton("Refresh reports");
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(e -> loadReports());

        panel.add(title, BorderLayout.WEST);
        panel.add(refreshButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Year");
        tableModel.addColumn("Month");
        tableModel.addColumn("Income");
        tableModel.addColumn("Card expenses");
        tableModel.addColumn("Cash expenses");
        tableModel.addColumn("Total expenses");
        tableModel.addColumn("Balance");
        tableModel.addColumn("Cumulative balance");

        reportTable = new JTable(tableModel);
        reportTable.setRowHeight(32);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportTable.setGridColor(Color.LIGHT_GRAY);
        reportTable.setShowGrid(true);
        reportTable.setFillsViewportHeight(true);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        reportTable.getTableHeader().setReorderingAllowed(false);
        reportTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setPreferredSize(new Dimension(800, 350));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadReports() {
        tableModel.setRowCount(0);

        List<MonthlyReport> reports = financeService.createAllMonthlyReports();

        for (MonthlyReport report : reports) {
            tableModel.addRow(new Object[]{
                    report.getYear(),
                    report.getMonth(),
                    String.format("%.2f", report.getTotalIncome()),
                    String.format("%.2f", report.getTotalCardExpense()),
                    String.format("%.2f", report.getTotalCashExpense()),
                    String.format("%.2f", report.getTotalExpense()),
                    String.format("%.2f", report.getBalance()),
                    String.format("%.2f", report.getCumulativeBalance())
            });
        }
    }
}