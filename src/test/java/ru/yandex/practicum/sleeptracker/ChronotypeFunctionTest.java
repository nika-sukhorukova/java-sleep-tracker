package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeFunctionTest {

    @Test
    @DisplayName("Определяет сову")
    void detectsOwl() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:30", "02.10.25 09:30", SleepQuality.GOOD),
                TestSessions.of("02.10.25 23:40", "03.10.25 10:00", SleepQuality.GOOD),
                TestSessions.of("03.10.25 21:00", "04.10.25 05:00", SleepQuality.GOOD) // жаворонок
        );

        SleepAnalysisResult<Chronotype> result = new ChronotypeFunction().apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    @DisplayName("Определяет жаворонка")
    void detectsLark() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 21:00", "02.10.25 05:00", SleepQuality.GOOD),
                TestSessions.of("02.10.25 21:30", "03.10.25 06:30", SleepQuality.GOOD),
                TestSessions.of("03.10.25 22:30", "04.10.25 08:00", SleepQuality.GOOD) // голубь
        );

        SleepAnalysisResult<Chronotype> result = new ChronotypeFunction().apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    @DisplayName("При ничье относит к голубю")
    void tieResolvesToDove() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 23:30", "02.10.25 09:30", SleepQuality.GOOD), // сова
                TestSessions.of("02.10.25 21:00", "03.10.25 05:00", SleepQuality.GOOD)  // жаворонок
        );

        SleepAnalysisResult<Chronotype> result = new ChronotypeFunction().apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    @DisplayName("Игнорирует дневные сессии")
    void ignoresDaytimeSessions() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 21:00", "02.10.25 05:00", SleepQuality.GOOD), // жаворонок
                TestSessions.of("02.10.25 13:00", "02.10.25 14:00", SleepQuality.NORMAL) // дневной — игнор
        );

        SleepAnalysisResult<Chronotype> result = new ChronotypeFunction().apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    @DisplayName("Без ночных сессий — голубь")
    void noNightSessionsGivesDove() {
        List<SleepingSession> sessions = List.of(
                TestSessions.of("01.10.25 14:00", "01.10.25 15:00", SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Chronotype> result = new ChronotypeFunction().apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }
}
