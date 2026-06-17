package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinDurationFunctionTest {

    @Test
    @DisplayName("Находит самую короткую сессию")
    void findsShortestSession() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD), // 480
                TestSessions.of("03.10.25 14:00", "03.10.25 14:50", SleepQuality.NORMAL) // 50
        );

        SleepAnalysisResult<Long> result = new MinDurationFunction().apply(sessions);

        assertEquals(50L, result.getValue());
    }

    @Test
    @DisplayName("Возвращает 0 для пустого лога")
    void returnsZeroForEmptyLog() {
        SleepAnalysisResult<Long> result = new MinDurationFunction().apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
