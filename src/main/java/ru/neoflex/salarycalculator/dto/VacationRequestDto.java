package ru.neoflex.salarycalculator.dto;

import java.math.BigDecimal;

public class VacationRequestDto {

    private String startDate;
    private BigDecimal averageMonthlySalary;
    private int vacationDays;

    public VacationRequestDto() {
    }

    public VacationRequestDto(BigDecimal averageMonthlySalary, int vacationDays) {
        this.averageMonthlySalary = averageMonthlySalary;
        this.vacationDays = vacationDays;
    }

    public VacationRequestDto(BigDecimal averageMonthlySalary, int vacationDays, String startDate) {
        this.averageMonthlySalary = averageMonthlySalary;
        this.vacationDays = vacationDays;
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
