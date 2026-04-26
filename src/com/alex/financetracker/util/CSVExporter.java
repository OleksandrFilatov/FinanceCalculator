package com.alex.financetracker.util;

import com.alex.financetracker.entity.MonthlyReport;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public void exportMonthlyReports(List<MonthlyReport> reports, String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("Year,Month,Income,Card expenses,Cash expenses,Total expenses,Balance,Cumulative balance\n");

            for (MonthlyReport report : reports) {
                writer.write(
                        report.getYear() + "," +
                                report.getMonth() + "," +
                                report.getTotalIncome() + "," +
                                report.getTotalCardExpense() + "," +
                                report.getTotalCashExpense() + "," +
                                report.getTotalExpense() + "," +
                                report.getBalance() + "," +
                                report.getCumulativeBalance() + "\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV export failed", e);
        }
    }
}