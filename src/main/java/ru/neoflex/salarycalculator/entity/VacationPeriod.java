package ru.neoflex.salarycalculator.entity;

import java.time.LocalDate;
import java.util.List;

public class VacationPeriod {
    private final int calendarDays;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int paidDays;
    private final List<LocalDate> holidays;

    public VacationPeriod(int calendarDays, LocalDate startDate, LocalDate endDate,
                          int paidDays, List<LocalDate> holidays) {
        this.calendarDays = calendarDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidDays = paidDays;
        this.holidays = holidays;
    }

    public int getCalendarDays() { return calendarDays; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getPaidDays() { return paidDays; }
    public List<LocalDate> getHolidays() { return holidays; }

    @Override
    public String toString() {
        return "Vacation{" +
                "calendarDays=" + calendarDays +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", paidDays=" + paidDays +
                ", holidays=" + holidays +
                '}';
    }

}
