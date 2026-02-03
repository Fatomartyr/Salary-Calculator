package ru.neoflex.salarycalculator.dto;

import java.math.BigDecimal;

public class VacationRequestDto {
    private BigDecimal averageMonthlySalary;
    private int vacationDays;


    public VacationRequestDto() {
    }

    public VacationRequestDto(BigDecimal averageMonthlySalary, int vacationDays) {
        this.averageMonthlySalary = averageMonthlySalary;
        this.vacationDays = vacationDays;
    }

    public BigDecimal getAverageMonthlySalary() {
        return averageMonthlySalary;
    }


    public void setAverageMonthlySalary(BigDecimal averageMonthlySalary) {
        this.averageMonthlySalary = averageMonthlySalary;
    }


    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

}
