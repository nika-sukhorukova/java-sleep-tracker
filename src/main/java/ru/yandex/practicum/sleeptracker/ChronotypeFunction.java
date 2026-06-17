package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotype>> {

    private static final String DESCRIPTION = "Хронотип пользователя";
    private static final LocalTime OWL_BEDTIME = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKEUP = LocalTime.of(9, 0);
    private static final LocalTime LARK_BEDTIME = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKEUP = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> data) {
        // Учитываем только ночные сессии: дневной сон и бессонные ночи игнорируются.
        Map<Chronotype, Long> counts = data.stream()
                .filter(SleepStatistics::isNightSleep)
                .collect(Collectors.groupingBy(this::classify, Collectors.counting()));

        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long doves = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype result;
        if (owls > larks && owls > doves) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.DOVE;
        }

        return new SleepAnalysisResult<>(DESCRIPTION, result);
    }

    private Chronotype classify(SleepingSession session) {
        LocalTime bedtime = session.getStart().toLocalTime();
        LocalTime wakeup = session.getEnd().toLocalTime();

        if (bedtime.isAfter(OWL_BEDTIME) && wakeup.isAfter(OWL_WAKEUP)) {
            return Chronotype.OWL;
        }
        if (bedtime.isBefore(LARK_BEDTIME) && wakeup.isBefore(LARK_WAKEUP)) {
            return Chronotype.LARK;
        }
        return Chronotype.DOVE;
    }
}
