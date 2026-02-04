package ru.neoflex.salarycalculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.service.VacationPayService;

import java.math.BigDecimal;

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
            @RequestParam int vacationDays,
            @RequestParam(required = false) String startDate) {

        VacationRequestDto request = new VacationRequestDto(averageMonthlySalary, vacationDays, startDate);
        return vacationPayService.calculateVacation(request);
    }

}
