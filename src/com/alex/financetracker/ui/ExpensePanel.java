package com.alex.financetracker.ui;

import com.alex.financetracker.entity.Expense;
import com.alex.financetracker.entity.ExpenseType;
import com.alex.financetracker.repository.ExpenseRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpensePanel extends JPanel {

    private JComboBox<Integer> yearBox;
    private JComboBox<String> monthBox;
    private JTextField amountField;
    private JComboBox<ExpenseType> typeBox;
    private JTextField categoryField;
    private JTextField descriptionField;

    private JTable expenseTable;
    private DefaultTableModel tableModel;

    private ExpenseRepository expenseRepository;

    public ExpensePanel() {
        expenseRepository = new ExpenseRepository();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadExpenses();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add New Expense");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 6, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);

        yearBox = new JComboBox<>(new Integer[]{
                2024, 2025, 2026, 2027
        });

        monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        });

        amountField = new JTextField();
        typeBox = new JComboBox<>(ExpenseType.values());
        categoryField = new JTextField();
        descriptionField = new JTextField();

        fieldsPanel.add(new JLabel("Year"));
        fieldsPanel.add(new JLabel("Month"));
        fieldsPanel.add(new JLabel("Amount"));
        fieldsPanel.add(new JLabel("Type"));
        fieldsPanel.add(new JLabel("Category"));
        fieldsPanel.add(new JLabel("Description"));

        fieldsPanel.add(yearBox);
        fieldsPanel.add(monthBox);
        fieldsPanel.add(amountField);
        fieldsPanel.add(typeBox);
        fieldsPanel.add(categoryField);
        fieldsPanel.add(descriptionField);

        JButton addButton = new JButton("Add expense");
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setPreferredSize(new Dimension(180, 45));
        addButton.addActionListener(e -> addExpense());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Expense List");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Year");
        tableModel.addColumn("Month");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Type");
        tableModel.addColumn("Category");
        tableModel.addColumn("Description");

        expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(32);
        expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expenseTable.setGridColor(Color.LIGHT_GRAY);
        expenseTable.setShowGrid(true);
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        expenseTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        expenseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        expenseTable.getTableHeader().setReorderingAllowed(false);
        expenseTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JButton deleteButton = new JButton("Delete selected");
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> deleteExpense());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(deleteButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addExpense() {
        try {
            int year = (Integer) yearBox.getSelectedItem();
            int month = monthBox.getSelectedIndex() + 1;
            double amount = Double.parseDouble(amountField.getText());
            ExpenseType type = (ExpenseType) typeBox.getSelectedItem();
            String category = categoryField.getText();
            String description = descriptionField.getText();

            Expense expense = new Expense(year, month, amount, type, category, description);

            expenseRepository.save(expense);

            JOptionPane.showMessageDialog(this, "Expense added successfully!");

            yearBox.setSelectedIndex(0);
            monthBox.setSelectedIndex(0);
            amountField.setText("");
            typeBox.setSelectedIndex(0);
            categoryField.setText("");
            descriptionField.setText("");

            loadExpenses();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        expenseRepository.deleteById(id);

        JOptionPane.showMessageDialog(this, "Expense deleted successfully!");

        loadExpenses();
    }

    private void loadExpenses() {
        tableModel.setRowCount(0);

        List<Expense> expenses = expenseRepository.findAll();

        for (Expense expense : expenses) {
            tableModel.addRow(new Object[]{
                    expense.getId(),
                    expense.getYear(),
                    expense.getMonth(),
                    expense.getAmount(),
                    expense.getExpenseType(),
                    expense.getCategory(),
                    expense.getDescription()
            });
        }
    }
}