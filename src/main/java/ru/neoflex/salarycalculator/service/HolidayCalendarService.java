package ru.neoflex.salarycalculator.service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HolidayCalendarService implements HolidayCalendar {

    /*
    Нерабочие праздничные дни в 2026 году
    Статьей 112 Трудового кодекса Российской Федерации установлены следующие
    нерабочие праздничные дни в Российской Федерации:

    1, 2, 3, 4, 5, 6 и 8 января — Новогодние каникулы;
    7 января — Рождество Христово;
    23 февраля — День защитника Отечества;
    8 марта — Международный женский день;
    1 мая — Праздник Весны и Труда;
    9 мая — День Победы;
    12 июня — День России;
    4 ноября — День народного единства.

    */

    private static final Set<MonthDay> HOLIDAYS = Set.of(
            MonthDay.of(1, 1), MonthDay.of(1, 2), MonthDay.of(1, 3),
            MonthDay.of(1, 4), MonthDay.of(1, 5), MonthDay.of(1, 6),
            MonthDay.of(1, 7), MonthDay.of(1, 8),

            MonthDay.of(2, 23),
            MonthDay.of(3, 8),
            MonthDay.of(5, 1),
            MonthDay.of(5, 9),
            MonthDay.of(6, 12),
            MonthDay.of(11, 4)

    );

    @Override
    public boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(MonthDay.from(date));
    }

    @Override
    public List<LocalDate> getHolidaysBetween(LocalDate from, LocalDate to) {
        List<LocalDate> holidays = new ArrayList<>();
        int year = to.getYear();
        for (MonthDay monthDay : HOLIDAYS) {
            LocalDate holidayDate = monthDay.atYear(year);
            if (!holidayDate.isBefore(from) && !holidayDate.isAfter(to)) {
                holidays.add(holidayDate);
            }
        }
        return holidays;
    }
}
