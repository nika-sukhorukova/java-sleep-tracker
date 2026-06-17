package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final String DESCRIPTION = "Бессонных ночей";
    private static final LocalTime NOON = LocalTime.NOON;

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> data) {
        if (data.isEmpty()) {
            return new SleepAnalysisResult<>(DESCRIPTION, 0L);
        }

        Set<LocalDate> sleptNights = data.stream()
                .filter(SleepStatistics::isNightSleep)
                .map(SleepStatistics::nightDate)
                .collect(Collectors.toSet());

        LocalDateTime firstStart = data.getFirst().getStart();
        LocalDateTime lastEnd = data.getLast().getEnd();

        LocalDate firstNight = firstStart.toLocalTime().isBefore(NOON)
                ? firstStart.toLocalDate()
                : firstStart.toLocalDate().plusDays(1);
        LocalDate lastNight = lastEnd.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(firstNight, lastNight) + 1;
        long sleeplessNights = Math.max(0, totalNights - sleptNights.size());

        return new SleepAnalysisResult<>(DESCRIPTION, sleeplessNights);
    }
}
