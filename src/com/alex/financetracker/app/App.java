package com.alex.financetracker.app;

import com.alex.financetracker.config.DatabaseInitializer;
import com.alex.financetracker.ui.MainFrame;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}