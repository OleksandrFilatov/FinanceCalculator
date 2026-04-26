package com.alex.financetracker.ui;

import com.alex.financetracker.entity.Income;
import com.alex.financetracker.repository.IncomeRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IncomePanel extends JPanel {

    private JComboBox<Integer> yearBox;
    private JComboBox<String> monthBox;
    private JTextField amountField;
    private JTextField descriptionField;

    private JTable incomeTable;
    private DefaultTableModel tableModel;

    private IncomeRepository incomeRepository;

    public IncomePanel() {
        incomeRepository = new IncomeRepository();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        addTableSelectionListener();

        loadIncomes();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add / Update Income");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);

        yearBox = new JComboBox<>(new Integer[]{2024, 2025, 2026, 2027});

        monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        });

        amountField = new JTextField();
        descriptionField = new JTextField();

        fieldsPanel.add(new JLabel("Year"));
        fieldsPanel.add(new JLabel("Month"));
        fieldsPanel.add(new JLabel("Amount"));
        fieldsPanel.add(new JLabel("Description"));

        fieldsPanel.add(yearBox);
        fieldsPanel.add(monthBox);
        fieldsPanel.add(amountField);
        fieldsPanel.add(descriptionField);

        JButton addButton = new JButton("Add income");
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setPreferredSize(new Dimension(180, 45));
        addButton.addActionListener(e -> addIncome());

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

        JLabel title = new JLabel("Income List");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Year");
        tableModel.addColumn("Month");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Description");

        incomeTable = new JTable(tableModel);
        incomeTable.setRowHeight(32);
        incomeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incomeTable.setGridColor(Color.LIGHT_GRAY);
        incomeTable.setShowGrid(true);
        incomeTable.setFillsViewportHeight(true);
        incomeTable.setFont(new Font("Arial", Font.PLAIN, 14));
        incomeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        incomeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        incomeTable.getTableHeader().setReorderingAllowed(false);
        incomeTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(incomeTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JButton updateButton = new JButton("Update selected");
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.addActionListener(e -> updateIncome());

        JButton deleteButton = new JButton("Delete selected");
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> deleteIncome());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addTableSelectionListener() {
        incomeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = incomeTable.getSelectedRow();

                if (selectedRow != -1) {
                    int year = (int) tableModel.getValueAt(selectedRow, 1);
                    int month = (int) tableModel.getValueAt(selectedRow, 2);
                    double amount = (double) tableModel.getValueAt(selectedRow, 3);
                    String description = (String) tableModel.getValueAt(selectedRow, 4);

                    yearBox.setSelectedItem(year);
                    monthBox.setSelectedIndex(month - 1);
                    amountField.setText(String.valueOf(amount));
                    descriptionField.setText(description);
                }
            }
        });
    }

    private void addIncome() {
        try {
            int year = (Integer) yearBox.getSelectedItem();
            int month = monthBox.getSelectedIndex() + 1;

            String amountText = amountField.getText().trim();
            String description = descriptionField.getText().trim();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Amount is required");
                return;
            }

            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0");
                return;
            }

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description is required");
                return;
            }

            Income income = new Income(year, month, amount, description);
            incomeRepository.save(income);

            JOptionPane.showMessageDialog(this, "Income added successfully!");

            clearFields();
            loadIncomes();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error");
        }
    }

    private void updateIncome() {
        int selectedRow = incomeTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int year = (Integer) yearBox.getSelectedItem();
            int month = monthBox.getSelectedIndex() + 1;

            String amountText = amountField.getText().trim();
            String description = descriptionField.getText().trim();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Amount is required");
                return;
            }

            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0");
                return;
            }

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description is required");
                return;
            }

            Income income = new Income(id, year, month, amount, description);
            incomeRepository.update(income);

            JOptionPane.showMessageDialog(this, "Income updated successfully!");

            clearFields();
            loadIncomes();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error");
        }
    }

    private void deleteIncome() {
        int selectedRow = incomeTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        incomeRepository.deleteById(id);

        JOptionPane.showMessageDialog(this, "Income deleted successfully!");

        loadIncomes();
    }

    private void loadIncomes() {
        tableModel.setRowCount(0);

        List<Income> incomes = incomeRepository.findAll();

        for (Income income : incomes) {
            tableModel.addRow(new Object[]{
                    income.getId(),
                    income.getYear(),
                    income.getMonth(),
                    income.getAmount(),
                    income.getDescription()
            });
        }
    }

    private void clearFields() {
        yearBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        amountField.setText("");
        descriptionField.setText("");
    }
}