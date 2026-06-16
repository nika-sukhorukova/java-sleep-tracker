package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.LongStream;

public final class SleepStatistics {

    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private SleepStatistics() {
    }

    public static LongStream durationsInMinutes(List<SleepingSession> sessions) {
        return sessions.stream().mapToLong(SleepingSession::durationMinutes);
    }

    public static boolean isNightSleep(SleepingSession session) {
        boolean crossesMidnight = !session.getStart().toLocalDate().equals(session.getEnd().toLocalDate());
        boolean startsBeforeSix = session.getStart().toLocalTime().isBefore(NIGHT_END);
        return crossesMidnight || startsBeforeSix;
    }

    public static LocalDate nightDate(SleepingSession session) {
        boolean crossesMidnight = !session.getStart().toLocalDate().equals(session.getEnd().toLocalDate());
        return crossesMidnight ? session.getEnd().toLocalDate() : session.getStart().toLocalDate();
    }
}
