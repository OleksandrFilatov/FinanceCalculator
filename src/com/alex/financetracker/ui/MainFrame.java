package com.alex.financetracker.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Finance Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel incomePanel = new JPanel();
        JPanel expensePanel = new JPanel();
        JPanel reportPanel = new JPanel();

        tabbedPane.addTab("Incomes", incomePanel);
        tabbedPane.addTab("Expenses", expensePanel);
        tabbedPane.addTab("Reports", reportPanel);

        add(tabbedPane);
    }
}
