package ru.neoflex.salarycalculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.entity.VacationPeriod;
import ru.neoflex.salarycalculator.properties.VacationProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VacationPayService {

    private static final BigDecimal AVERAGE_DAYS_IN_MONTH = new BigDecimal("29.3");
    private static final int SCALE = 2;
    private final VacationDaysCalculator daysCalculator;
    private final BigDecimal taxRate;

    public VacationPayService(VacationDaysCalculator daysCalculator,
                              VacationProperties properties) {
        this.daysCalculator = daysCalculator;
        this.taxRate = properties.getTaxRate();
    }

    public VacationResponseDto calculateVacation(VacationRequestDto req) {
        BigDecimal avgMonth = req.getAverageMonthlySalary();
        int days = req.getVacationDays();

        BigDecimal dailyRate = calculateDailyRate(avgMonth);
        BigDecimal gross = dailyRate.multiply(BigDecimal.valueOf(days));
        BigDecimal net = applyTax(gross);

        VacationPeriod vacationPeriod = daysCalculator.getVacationDateInfo(req.getStartDate(), req.getVacationDays());
        return new VacationResponseDto(
                dailyRate,
                gross.setScale(SCALE, RoundingMode.HALF_UP),
                net.setScale(SCALE, RoundingMode.HALF_UP),
                vacationPeriod.getPaidDays(),
                vacationPeriod.getCalendarDays(),
                vacationPeriod.getStartDate(),
                vacationPeriod.getEndDate()
        );
    }

    private BigDecimal calculateDailyRate(BigDecimal averageMonthlySalary) {
        return averageMonthlySalary.divide(AVERAGE_DAYS_IN_MONTH, SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal applyTax(BigDecimal gross) {
        BigDecimal multiplier = BigDecimal.ONE.subtract(taxRate);
        return gross.multiply(multiplier);
    }

}
