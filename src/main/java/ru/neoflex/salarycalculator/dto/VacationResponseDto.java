package ru.neoflex.salarycalculator.dto;

import java.math.BigDecimal;

public class VacationResponseDto {
    private BigDecimal dailyRate;
    private BigDecimal vacationPay;


    public VacationResponseDto() {
    }


    public VacationResponseDto(BigDecimal dailyRate, BigDecimal vacationPay) {
        this.dailyRate = dailyRate;
        this.vacationPay = vacationPay;
    }


    public BigDecimal getDailyRate() {
        return dailyRate;
    }


    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }


    public BigDecimal getVacationPay() {
        return vacationPay;
    }


    public void setVacationPay(BigDecimal vacationPay) {
        this.vacationPay = vacationPay;
    }
}
