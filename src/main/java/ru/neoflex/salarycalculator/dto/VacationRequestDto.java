package ru.neoflex.salarycalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VacationRequestDto {
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    private BigDecimal averageMonthlySalary;
    private int vacationDays;


    public VacationRequestDto() {
    }

    public VacationRequestDto(LocalDate startDate, BigDecimal averageMonthlySalary, int vacationDays) {
        this.startDate = startDate;
        this.averageMonthlySalary = averageMonthlySalary;
        this.vacationDays = vacationDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
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
