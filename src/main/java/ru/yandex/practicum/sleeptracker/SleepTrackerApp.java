package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> FUNCTIONS = List.of(
            new SessionCountFunction(),
            new MinDurationFunction(),
            new MaxDurationFunction(),
            new AverageDurationFunction(),
            new BadSleepCountFunction(),
            new SleeplessNightsFunction(),
            new ChronotypeFunction()
    );

    public static void main(String[] args) {
        List<SleepingSession> sessions = readSessions(args[0]);

        FUNCTIONS.forEach(function -> {
            SleepAnalysisResult<?> result = function.apply(sessions);
            System.out.println(result.getDescription() + ": " + result.getValue());
        });
    }

    static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");

        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2].trim());

        return new SleepingSession(start, end, quality);
    }

    static List<SleepingSession> readSessions(String path) {
        try {
            return Files.readAllLines(Path.of(path)).stream()
                    .map(SleepTrackerApp::parseLine)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + path, e);
        }
    }
}