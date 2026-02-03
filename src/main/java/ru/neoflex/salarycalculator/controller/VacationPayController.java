package ru.neoflex.salarycalculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.service.VacationPayService;

@RestController
@RequestMapping("/")
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }


        @GetMapping("/calculate")
        public VacationResponseDto calculate(@ModelAttribute VacationRequestDto request) {
            return vacationPayService.calculateVacation(request);
        }
}
