package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {

    private static final String DESCRIPTION = "Средняя продолжительность сессии (мин)";

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> data) {
        double avg = SleepStatistics.durationsInMinutes(data)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult<>(DESCRIPTION, avg);
    }
}
