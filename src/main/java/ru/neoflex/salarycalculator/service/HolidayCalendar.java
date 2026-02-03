package ru.neoflex.salarycalculator.service;

import java.time.LocalDate;
import java.util.List;

public interface HolidayCalendar {
    boolean isHoliday(LocalDate date);
    List<LocalDate> getHolidaysBetween(LocalDate from, LocalDate to);
}
