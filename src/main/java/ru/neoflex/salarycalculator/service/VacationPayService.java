package ru.neoflex.salarycalculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.entity.VacationPeriod;
import ru.neoflex.salarycalculator.exception.BadRequestException;
import ru.neoflex.salarycalculator.exception.DateParseException;
import ru.neoflex.salarycalculator.properties.VacationProperties;
import ru.neoflex.salarycalculator.util.DateParseUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class VacationPayService {

    private static final Logger log = LoggerFactory.getLogger(VacationPayService.class);
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

        if (req == null) {
            log.warn("Request body is null");
            throw new BadRequestException("Request body is required");
        }

        BigDecimal avgMonth = req.getAverageMonthlySalary();
        if (avgMonth == null) {
            log.warn("averageMonthlySalary is null");
            throw new BadRequestException("averageMonthlySalary is required");
        }
        if (avgMonth.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid averageMonthlySalary={}", avgMonth);
            throw new BadRequestException("averageMonthlySalary must be greater than zero");
        }

        int days = req.getVacationDays();
        if (days <= 0) {
            log.warn("Invalid vacationDays={}", days);
            throw new BadRequestException("vacationDays must be greater than zero");
        }

        LocalDate startDate;
        String startDateStr = req.getStartDate();
        if (startDateStr == null || startDateStr.isEmpty()) {
            startDate = LocalDate.now();
            log.info("startDate not provided, using now={}", startDate);
        } else {
            try {
                startDate = DateParseUtil.parseDateWithFallback(startDateStr);
                log.info("Parsed startDate={} from '{}'",
                        startDate, startDateStr);
            } catch (DateParseException ex) {
                log.info("Date parse error for input '{}': {}",
                        startDateStr, ex.getMessage());
                log.info("Date parse error for input '{}': {}", startDateStr, ex.getMessage());
                throw new DateParseException(
                        String.format("Failed to parse startDate '%s'", startDateStr), ex);
            }
        }

        BigDecimal dailyRate = calculateDailyRate(avgMonth);
        BigDecimal gross = dailyRate.multiply(BigDecimal.valueOf(days));
        BigDecimal net = applyTax(gross);
        log.info("Calculated rates: dailyRate={}, gross={}, net={}", dailyRate, gross, net);

        VacationPeriod vacationPeriod = daysCalculator.getVacationDateInfo(startDate, req.getVacationDays());
        log.info(
                "Vacation period calculated: start={}, end={}, paidDays={}, calendarDays={}, holidays={}",
                vacationPeriod.getStartDate(),
                vacationPeriod.getEndDate(),
                vacationPeriod.getPaidDays(),
                vacationPeriod.getCalendarDays(),
                vacationPeriod.getHolidays()
        );
        VacationResponseDto response = new VacationResponseDto(
                dailyRate,
                gross.setScale(SCALE, RoundingMode.HALF_UP),
                net.setScale(SCALE, RoundingMode.HALF_UP),
                vacationPeriod.getPaidDays(),
                vacationPeriod.getCalendarDays(),
                vacationPeriod.getStartDate(),
                vacationPeriod.getEndDate(),
                vacationPeriod.getHolidays()
        );

        log.info("calculateVacation finished successfully");

        return response;
    }

    private BigDecimal calculateDailyRate(BigDecimal averageMonthlySalary) {
        return averageMonthlySalary.divide(AVERAGE_DAYS_IN_MONTH, SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal applyTax(BigDecimal gross) {
        BigDecimal multiplier = BigDecimal.ONE.subtract(taxRate);
        return gross.multiply(multiplier);
    }

}
