package ru.neoflex.salarycalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.salarycalculator.dto.VacationRequestDto;
import ru.neoflex.salarycalculator.dto.VacationResponseDto;
import ru.neoflex.salarycalculator.entity.VacationPeriod;
import ru.neoflex.salarycalculator.exception.BadRequestException;
import ru.neoflex.salarycalculator.exception.DateParseException;
import ru.neoflex.salarycalculator.properties.VacationProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceTest {

    @Mock
    private VacationDaysCalculator daysCalculator;

    @Mock
    private VacationProperties properties;

    private VacationPayService service;

    private final BigDecimal taxRate = new BigDecimal("0.13");

    @BeforeEach
    void setUp() {
    }

    @Test
    void calculateVacation_happyPath_parsesDateAndCalculatesValues() {
        when(properties.getTaxRate()).thenReturn(taxRate);
        service = new VacationPayService(daysCalculator, properties);

        BigDecimal avgMonth = new BigDecimal("100000");
        int vacationDays = 14;
        String startDateStr = "09.03.2026";

        LocalDate startDate = LocalDate.of(2026, 3, 9);
        LocalDate endDate = LocalDate.of(2026, 3, 22);

        VacationPeriod period = new VacationPeriod(
                vacationDays,
                startDate,
                endDate,
                vacationDays,
                List.of()
        );

        when(daysCalculator.getVacationDateInfo(any(LocalDate.class), eq(vacationDays)))
                .thenReturn(period);

        VacationRequestDto request =
                new VacationRequestDto(avgMonth, vacationDays, startDateStr);

        VacationResponseDto resp = service.calculateVacation(request);

        BigDecimal expectedDaily =
                avgMonth.divide(new BigDecimal("29.3"), 2, RoundingMode.HALF_UP);
        BigDecimal expectedGross =
                expectedDaily.multiply(BigDecimal.valueOf(vacationDays))
                        .setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedNet =
                expectedGross.multiply(BigDecimal.ONE.subtract(taxRate))
                        .setScale(2, RoundingMode.HALF_UP);

        assertThat(resp.getDailyRate()).isEqualByComparingTo(expectedDaily);
        assertThat(resp.getGrossPay()).isEqualByComparingTo(expectedGross);
        assertThat(resp.getNetPay()).isEqualByComparingTo(expectedNet);

        ArgumentCaptor<LocalDate> captor = ArgumentCaptor.forClass(LocalDate.class);
        verify(daysCalculator).getVacationDateInfo(captor.capture(), eq(vacationDays));
        assertThat(captor.getValue()).isEqualTo(startDate);

        assertThat(resp.getPaidDays()).isEqualTo(vacationDays);
        assertThat(resp.getCalendarDays()).isEqualTo(vacationDays);
        assertThat(resp.getStartDate()).isEqualTo(startDate);
        assertThat(resp.getEndDate()).isEqualTo(endDate);
        assertThat(resp.getHolidays()).isEmpty();
    }

    @Test
    void calculateVacation_whenStartDateNull_usesNowAndReturnsResult() {
        when(properties.getTaxRate()).thenReturn(taxRate);
        service = new VacationPayService(daysCalculator, properties);

        BigDecimal avgMonth = new BigDecimal("50000");
        int vacationDays = 7;
        VacationRequestDto request = new VacationRequestDto(avgMonth, vacationDays, null);

        VacationPeriod period = new VacationPeriod(
                vacationDays,
                LocalDate.now(),
                LocalDate.now().plusDays(vacationDays - 1),
                vacationDays,
                List.of()
        );

        when(daysCalculator.getVacationDateInfo(any(LocalDate.class), eq(vacationDays)))
                .thenReturn(period);

        VacationResponseDto resp = service.calculateVacation(request);

        assertThat(resp).isNotNull();
        verify(daysCalculator, times(1))
                .getVacationDateInfo(any(LocalDate.class), eq(vacationDays));
    }

    @Test
    void calculateVacation_invalidDate_throwsDateParseException() {
        when(properties.getTaxRate()).thenReturn(taxRate);
        service = new VacationPayService(daysCalculator, properties);

        BigDecimal avgMonth = new BigDecimal("100000");
        int vacationDays = 10;
        String badDate = "not-a-date";
        VacationRequestDto request = new VacationRequestDto(avgMonth, vacationDays, badDate);

        assertThatThrownBy(() -> service.calculateVacation(request))
                .isInstanceOf(DateParseException.class)
                .hasMessageContaining("Failed to parse startDate");
    }

    @Test
    void calculateVacation_negativeSalary_throwsBadRequest() {
        service = new VacationPayService(daysCalculator, properties);

        BigDecimal avgMonth = new BigDecimal("-1");
        int vacationDays = 5;
        VacationRequestDto request = new VacationRequestDto(avgMonth, vacationDays, "01.03.2026");

        assertThatThrownBy(() -> service.calculateVacation(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("averageMonthlySalary must be greater than zero");
    }

    @Test
    void calculateVacation_zeroDays_throwsBadRequest() {
        service = new VacationPayService(daysCalculator, properties);

        BigDecimal avgMonth = new BigDecimal("100000");
        int vacationDays = 0;
        VacationRequestDto request = new VacationRequestDto(avgMonth, vacationDays, "01.03.2026");

        assertThatThrownBy(() -> service.calculateVacation(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("vacationDays must be greater than zero");
    }
}
