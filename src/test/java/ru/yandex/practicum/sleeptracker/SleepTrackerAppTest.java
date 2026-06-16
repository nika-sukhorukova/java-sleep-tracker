package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    @Test
    @DisplayName("parseLine разбирает строку лога в сессию")
    void parseLineParsesSingleRecord() {
        SleepingSession session = SleepTrackerApp.parseLine("01.10.25 23:15;02.10.25 07:30;GOOD");

        assertEquals(LocalDateTime.of(2025, 10, 1, 23, 15), session.getStart());
        assertEquals(LocalDateTime.of(2025, 10, 2, 7, 30), session.getEnd());
        assertEquals(SleepQuality.GOOD, session.getQuality());
    }

    @Test
    @DisplayName("parseLine игнорирует хвостовой пробел в качестве сна")
    void parseLineTrimsTrailingSpaceInQuality() {
        SleepingSession session = SleepTrackerApp.parseLine("03.10.25 23:30;04.10.25 06:20;BAD ");

        assertEquals(SleepQuality.BAD, session.getQuality());
    }

    @Test
    @DisplayName("readSessions читает все строки файла в список сессий")
    void readSessionsReadsAllLines(@TempDir Path dir) throws Exception {
        Path file = dir.resolve("log.txt");
        Files.write(file, List.of(
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "02.10.25 23:00;03.10.25 06:40;NORMAL"
        ));

        List<SleepingSession> sessions = SleepTrackerApp.readSessions(file.toString());

        assertEquals(2, sessions.size());
        assertEquals(SleepQuality.NORMAL, sessions.get(1).getQuality());
    }
}
