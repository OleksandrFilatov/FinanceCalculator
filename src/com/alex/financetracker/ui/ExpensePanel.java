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
    private JTextField searchField;

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

        addTableSelectionListener();

        loadExpenses();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add / Update Expense");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 6, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);

        yearBox = new JComboBox<>(new Integer[]{2024, 2025, 2026, 2027});

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

        searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.addActionListener(e -> searchExpenses());

        JButton resetButton = new JButton("Reset");
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.addActionListener(e -> {
            searchField.setText("");
            loadExpenses();
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search category:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

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

        JButton updateButton = new JButton("Update selected");
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.addActionListener(e -> updateExpense());

        JButton deleteButton = new JButton("Delete selected");
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> deleteExpense());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addExpense() {
        try {
            int year = (Integer) yearBox.getSelectedItem();
            int month = monthBox.getSelectedIndex() + 1;

            String amountText = amountField.getText().trim();
            ExpenseType type = (ExpenseType) typeBox.getSelectedItem();
            String category = categoryField.getText().trim();
            String description = descriptionField.getText().trim();

            if (!validateExpenseInput(amountText, category, description)) {
                return;
            }

            double amount = Double.parseDouble(amountText);

            Expense expense = new Expense(year, month, amount, type, category, description);
            expenseRepository.save(expense);

            JOptionPane.showMessageDialog(this, "Expense added successfully!");

            clearFields();
            loadExpenses();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error");
        }
    }

    private void updateExpense() {
        int selectedRow = expenseTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int year = (Integer) yearBox.getSelectedItem();
            int month = monthBox.getSelectedIndex() + 1;

            String amountText = amountField.getText().trim();
            ExpenseType type = (ExpenseType) typeBox.getSelectedItem();
            String category = categoryField.getText().trim();
            String description = descriptionField.getText().trim();

            if (!validateExpenseInput(amountText, category, description)) {
                return;
            }

            double amount = Double.parseDouble(amountText);

            Expense expense = new Expense(id, year, month, amount, type, category, description);
            expenseRepository.update(expense);

            JOptionPane.showMessageDialog(this, "Expense updated successfully!");

            clearFields();
            loadExpenses();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error");
        }
    }

    private boolean validateExpenseInput(String amountText, String category, String description) {
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Amount is required");
            return false;
        }

        double amount = Double.parseDouble(amountText);

        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Amount must be greater than 0");
            return false;
        }

        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category is required");
            return false;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description is required");
            return false;
        }

        return true;
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

        clearFields();
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

    private void searchExpenses() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadExpenses();
            return;
        }

        tableModel.setRowCount(0);

        List<Expense> expenses = expenseRepository.searchByCategory(keyword);

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

    private void addTableSelectionListener() {
        expenseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = expenseTable.getSelectedRow();

                if (selectedRow != -1) {
                    int year = (int) tableModel.getValueAt(selectedRow, 1);
                    int month = (int) tableModel.getValueAt(selectedRow, 2);
                    double amount = (double) tableModel.getValueAt(selectedRow, 3);
                    ExpenseType type = (ExpenseType) tableModel.getValueAt(selectedRow, 4);
                    String category = (String) tableModel.getValueAt(selectedRow, 5);
                    String description = (String) tableModel.getValueAt(selectedRow, 6);

                    yearBox.setSelectedItem(year);
                    monthBox.setSelectedIndex(month - 1);
                    amountField.setText(String.valueOf(amount));
                    typeBox.setSelectedItem(type);
                    categoryField.setText(category);
                    descriptionField.setText(description);
                }
            }
        });
    }

    private void clearFields() {
        yearBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        amountField.setText("");
        typeBox.setSelectedIndex(0);
        categoryField.setText("");
        descriptionField.setText("");
    }
}