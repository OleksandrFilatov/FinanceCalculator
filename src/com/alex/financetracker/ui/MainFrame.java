package com.alex.financetracker.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Finance Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Finance Tracker", SwingConstants.CENTER);
        add(label);
    }
}
