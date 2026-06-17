package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageDurationFunctionTest {

    @Test
    @DisplayName("Считает среднюю продолжительность")
    void computesAverageDuration() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD), // 480
                TestSessions.of("02.10.25 23:00", "03.10.25 05:00", SleepQuality.NORMAL) // 360
        );

        SleepAnalysisResult<Double> result = new AverageDurationFunction().apply(sessions);

        assertEquals(420.0, result.getValue());
    }

    @Test
    @DisplayName("Возвращает 0 для пустого лога")
    void returnsZeroForEmptyLog() {
        SleepAnalysisResult<Double> result = new AverageDurationFunction().apply(List.of());

        assertEquals(0.0, result.getValue());
    }
}
