package ru.neoflex.salarycalculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class VacationRequestDto {
    @Schema(description = "Дата начала в формате dd.MM.yyyy", example = "01.03.2026")
    private String startDate;
    @Schema(description = "Среднемесячная зарплата", example = "100000")
    private BigDecimal averageMonthlySalary;
    @Schema(description = "Количество оплачиваемых дней отпуска", example = "14")
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
