package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SessionCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {

    private static final String DESCRIPTION = "Всего сессий сна";

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> data) {
        return new SleepAnalysisResult<>(DESCRIPTION, data.size());
    }
}
