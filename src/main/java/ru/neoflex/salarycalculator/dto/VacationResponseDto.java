package ru.neoflex.salarycalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VacationResponseDto {

    private BigDecimal dailyRate;
    private BigDecimal grossPay;
    private BigDecimal netPay;
    private int paidDays;
    private int calendarDays;

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
            LocalDate endDate) {

        this.dailyRate = dailyRate;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.paidDays = paidDays;
        this.calendarDays = calendarDays;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BigDecimal getDailyRate() { return dailyRate; }
    public BigDecimal getGrossPay() { return grossPay; }
    public BigDecimal getNetPay() { return netPay; }
    public int getPaidDays() { return paidDays; }
    public int getCalendarDays() { return calendarDays; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

}
