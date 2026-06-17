package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final String DESCRIPTION = "Минимальная продолжительность сессии (мин)";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> data) {
        long min = SleepStatistics.durationsInMinutes(data)
                .min()
                .orElse(0);
        return new SleepAnalysisResult<>(DESCRIPTION, min);
    }
}
