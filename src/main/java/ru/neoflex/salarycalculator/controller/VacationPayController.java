package ru.neoflex.salarycalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Vacation calculator", description = "Расчёт отпускных выплат")
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @Operation(
            summary = "Рассчитать отпускные",
            description = "Рассчитывает сумму отпускных по средней зарплате и количеству дней",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @GetMapping("/calculate")
    public VacationResponseDto calculateVacation(
            @Parameter(description = "Среднемесячная зарплата", example = "100000", required = true)
            @RequestParam BigDecimal averageMonthlySalary,
            @Parameter(description = "Количество оплачиваемых дней отпуска", example = "14", required = true)
            @RequestParam int vacationDays,
            @Parameter(description = "Дата начала отпуска (dd.MM.yyyy)", example = "01.03.2026")
            @RequestParam(required = false) String startDate) {

        VacationRequestDto request =
                new VacationRequestDto(averageMonthlySalary, vacationDays, startDate);

        return vacationPayService.calculateVacation(request);
    }
}
