package ru.neoflex.salarycalculator.service;

import org.springframework.stereotype.Component;
import ru.neoflex.salarycalculator.entity.VacationPeriod;

import java.time.LocalDate;
import java.util.List;

@Component
public class VacationDaysCalculator {

    private final HolidayCalendar holidayCalendar;
    public VacationDaysCalculator(HolidayCalendar holidayCalendar) {
        this.holidayCalendar = holidayCalendar;
    }

    public VacationPeriod getVacationDateInfo(LocalDate startDate, int vacationDays) {
        LocalDate currentVacationDay = startDate;

        int paidDays = 0;
        int calendarDays = 0;

        while (paidDays < vacationDays) {
            calendarDays++;
            boolean isHoliday = holidayCalendar.isHoliday(currentVacationDay);

            if (!isHoliday) {
                paidDays++;
            }
            currentVacationDay = currentVacationDay.plusDays(1);
        }
        LocalDate endDate = startDate.plusDays(calendarDays - 1);
        List<LocalDate> holidays = holidayCalendar.getHolidaysBetween(startDate, endDate);
        return new VacationPeriod(calendarDays, startDate, endDate, vacationDays, holidays);
    }

}
