package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadSleepCountFunctionTest {

    @Test
    @DisplayName("Считает только сессии с плохим качеством")
    void countsOnlyBadSessions() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("02.10.25 23:00", "03.10.25 05:00", SleepQuality.BAD),
                TestSessions.of("03.10.25 23:00", "04.10.25 05:00", SleepQuality.BAD),
                TestSessions.of("04.10.25 23:00", "05.10.25 05:00", SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Long> result = new BadSleepCountFunction().apply(sessions);

        assertEquals(2L, result.getValue());
    }

    @Test
    @DisplayName("Возвращает 0, когда нет плохих сессий")
    void returnsZeroWhenNoBadSessions() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("02.10.25 23:00", "03.10.25 05:00", SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Long> result = new BadSleepCountFunction().apply(sessions);

        assertEquals(0L, result.getValue());
    }
}
