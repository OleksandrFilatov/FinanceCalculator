package com.alex.financetracker.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Finance Tracker");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        DashboardPanel dashboardPanel = new DashboardPanel();
        IncomePanel incomePanel = new IncomePanel();
        ExpensePanel expensePanel = new ExpensePanel();
        ReportPanel reportPanel = new ReportPanel();
        StatisticsPanel statisticsPanel = new StatisticsPanel();

        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Incomes", incomePanel);
        tabbedPane.addTab("Expenses", expensePanel);
        tabbedPane.addTab("Reports", reportPanel);
        tabbedPane.addTab("Statistics", statisticsPanel);

        add(tabbedPane);
    }
}
