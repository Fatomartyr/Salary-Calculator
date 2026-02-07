package ru.neoflex.salarycalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VacationResponseDto {

    private BigDecimal dailyRate;
    private BigDecimal grossPay;
    private BigDecimal netPay;
    private int paidDays;
    private int calendarDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private List<LocalDate> holidays;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    public VacationResponseDto() {
    }

    public VacationResponseDto(
            BigDecimal dailyRate,
            BigDecimal grossPay,
            BigDecimal netPay,
            Integer paidDays,
            Integer calendarDays,
            LocalDate startDate,
            LocalDate endDate,
            List<LocalDate> holidays) {

        this.dailyRate = dailyRate;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.paidDays = paidDays;
        this.calendarDays = calendarDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidays = holidays;
    }

    public BigDecimal getDailyRate() { return dailyRate; }
    public BigDecimal getGrossPay() { return grossPay; }
    public BigDecimal getNetPay() { return netPay; }
    public int getPaidDays() { return paidDays; }
    public int getCalendarDays() { return calendarDays; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public List<LocalDate> getHolidays() { return holidays; }

}
