package com.alex.financetracker.config;

public final class AppSettings {

    public static final int START_YEAR = 2026;
    public static final int END_YEAR = 2036;

    private AppSettings() {
    }

    public static Integer[] createYearRange() {
        Integer[] years = new Integer[END_YEAR - START_YEAR + 1];

        for (int i = 0; i < years.length; i++) {
            years[i] = START_YEAR + i;
        }

        return years;
    }
}
