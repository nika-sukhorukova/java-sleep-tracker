package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final String DESCRIPTION = "Сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> data) {
        long badCount = data.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>(DESCRIPTION, badCount);
    }
}
