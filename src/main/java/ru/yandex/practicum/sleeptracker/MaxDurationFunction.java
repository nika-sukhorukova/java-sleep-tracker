package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final String DESCRIPTION = "Максимальная продолжительность сессии (мин)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> data) {
        long max = SleepStatistics.durationsInMinutes(data)
                .max()
                .orElse(0);
        return new SleepAnalysisResult<>(DESCRIPTION, max);
    }
}
