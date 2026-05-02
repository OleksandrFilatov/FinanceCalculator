package com.alex.financetracker.ui;

import com.alex.financetracker.service.FinanceService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class StatisticsPanel extends JPanel {

    private JTable statisticsTable;
    private DefaultTableModel tableModel;

    private FinanceService financeService;

    public StatisticsPanel() {
        financeService = new FinanceService();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadStatistics();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Expense Statistics by Category");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(e -> loadStatistics());

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
        tableModel.addColumn("Category");
        tableModel.addColumn("Total amount");

        statisticsTable = new JTable(tableModel);
        statisticsTable.setRowHeight(32);
        statisticsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statisticsTable.setGridColor(Color.LIGHT_GRAY);
        statisticsTable.setShowGrid(true);
        statisticsTable.setFillsViewportHeight(true);
        statisticsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        statisticsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        statisticsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        statisticsTable.getTableHeader().setReorderingAllowed(false);
        statisticsTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(statisticsTable);
        scrollPane.setPreferredSize(new Dimension(800, 350));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadStatistics() {
        tableModel.setRowCount(0);

        Map<String, Double> categoryTotals = financeService.calculateExpensesByCategory();

        for (String category : categoryTotals.keySet()) {
            tableModel.addRow(new Object[]{
                    category,
                    String.format("%.2f", categoryTotals.get(category))
            });
        }
    }
}
