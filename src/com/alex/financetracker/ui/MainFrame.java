package com.alex.financetracker.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Finance Tracker");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        IncomePanel incomePanel = new IncomePanel();
        ExpensePanel expensePanel = new ExpensePanel();
        ReportPanel reportPanel = new ReportPanel();

        tabbedPane.addTab("Incomes", incomePanel);
        tabbedPane.addTab("Expenses", expensePanel);
        tabbedPane.addTab("Reports", reportPanel);

        add(tabbedPane);
    }
}
