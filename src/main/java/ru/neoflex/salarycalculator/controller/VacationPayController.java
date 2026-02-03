package ru.neoflex.salarycalculator.controller;

import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.service.VacationPayService;
import ru.neoflex.salarycalculator.util.DateParseUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/vacation-calculator")
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping
    public VacationResponseDto calculateVacation(
            @RequestParam BigDecimal averageMonthlySalary,
            @RequestParam Integer vacationDays,
            @RequestParam(required = false) String startDate) {

        LocalDate parsedStartDate = null;
        if (startDate != null && !startDate.trim().isEmpty()) {
            parsedStartDate = DateParseUtil.parseDateWithFallback(startDate);
        }
        VacationRequestDto request = new VacationRequestDto(
                parsedStartDate,
                averageMonthlySalary,
                vacationDays
        );

        return vacationPayService.calculateVacation(request);
    }

}
