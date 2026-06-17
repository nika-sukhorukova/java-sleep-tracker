package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsFunctionTest {

    @Test
    @DisplayName("Нет бессонных ночей, если спал каждую ночь")
    void noSleeplessNightsWhenEveryNightHasSleep() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("02.10.25 23:00", "03.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("03.10.25 23:00", "04.10.25 07:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Считает ночь без сна как бессонную")
    void countsNightWithoutAnySleep() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("03.10.25 23:00", "04.10.25 07:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Дневной сон не считается ночным")
    void daytimeNapDoesNotCountAsNightSleep() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("02.10.25 14:00", "02.10.25 15:00", SleepQuality.NORMAL), // дневной сон
                TestSessions.of("03.10.25 23:00", "04.10.25 07:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(sessions);

        // ночь со 02 на 03 всё равно бессонная — дневной сон не считается
        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Корректно работает на стыке месяцев")
    void worksAcrossMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("30.10.25 23:00", "31.10.25 07:00", SleepQuality.GOOD),
                TestSessions.of("31.10.25 23:00", "01.11.25 07:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Сон под утро считается ночным")
    void earlyMorningSessionCountsAsNightSleep() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("05.10.25 00:10", "05.10.25 06:20", SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Возвращает 0 для пустого лога")
    void returnsZeroForEmptyLog() {
        SleepAnalysisResult<Long> result = new SleeplessNightsFunction().apply(List.of());

        assertEquals(0L, result.getValue());
    }
}
