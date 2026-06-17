package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionCountFunctionTest {

    @Test
    @DisplayName("Считает количество сессий")
    void countsSeveralSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Integer> result = new SessionCountFunction().apply(sessions);

        assertEquals(2, result.getValue());
    }

    @Test
    @DisplayName("Возвращает 0 для пустого лога")
    void countsZeroForEmptyLog() {
        SleepAnalysisResult<Integer> result = new SessionCountFunction().apply(List.of());

        assertEquals(0, result.getValue());
    }
}
