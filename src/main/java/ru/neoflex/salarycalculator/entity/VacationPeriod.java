package ru.neoflex.salarycalculator.entity;

import java.time.LocalDate;

public class VacationPeriod {
    private final int calendarDays;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int paidDays;

    public VacationPeriod(int calendarDays, LocalDate startDate, LocalDate endDate, int paidDays) {
        this.calendarDays = calendarDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidDays = paidDays;
    }

    public int getCalendarDays() { return calendarDays; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getPaidDays() { return paidDays; }

    @Override
    public String toString() {
        return "Vacation{" +
                "calendarDays=" + calendarDays +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", paidDays=" + paidDays +
                '}';
    }

}
