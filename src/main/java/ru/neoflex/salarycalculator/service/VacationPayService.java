package ru.neoflex.salarycalculator.service;

import org.springframework.stereotype.Service;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VacationPayService {

    private static final BigDecimal AVERAGE_DAYS_IN_MONTH = new BigDecimal("29.3");

    public VacationResponseDto calculateVacation(VacationRequestDto req) {
        BigDecimal avgMonth = req.getAverageMonthlySalary();
        int days = req.getVacationDays();


        if (avgMonth == null) {
            avgMonth = BigDecimal.ZERO;
        }


        BigDecimal dailyRate = BigDecimal.ZERO;
        if (AVERAGE_DAYS_IN_MONTH.compareTo(BigDecimal.ZERO) != 0) {
            dailyRate = avgMonth.divide(AVERAGE_DAYS_IN_MONTH, 10, RoundingMode.HALF_UP);
        }


        BigDecimal vacationPay = dailyRate.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
        dailyRate = dailyRate.setScale(2, RoundingMode.HALF_UP);


        return new VacationResponseDto(dailyRate, vacationPay);
    }


}
