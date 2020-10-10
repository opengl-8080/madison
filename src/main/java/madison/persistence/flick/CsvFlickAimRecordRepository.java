package madison.persistence.flick;

import madison.domain.flick.FlickAimAccuracy;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordDate;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.FlickAimRound;
import madison.domain.flick.FlickAimScore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CsvFlickAimRecordRepository implements FlickAimRecordRepository {
    private final Path file;
    private final Map<FlickAimRecordDate, FlickAimRecord> cache = new HashMap<>();

    public CsvFlickAimRecordRepository(Path file) {
        this.file = Objects.requireNonNull(file);
        load();
    }

    @Override
    public void register(FlickAimRecord record) {
        cache.put(record.date(), record);
        save();
    }

    @Override
    public List<FlickAimRecord> findAllOrderByDate() {
        return cache.values()
                .stream()
                .sorted(Comparator.comparing(FlickAimRecord::date))
                .collect(Collectors.toList());
    }

    private void load() {
        if (!(Files.exists(file) && Files.isRegularFile(file))) {
            return;
        }
        
        try {
            final Map<FlickAimRecordDate, List<FlickAimRound>> records = new HashMap<>();
            Files.lines(file, StandardCharsets.UTF_8)
                .forEach(line -> {
                    final String[] elements = line.split(",");
                    
                    final FlickAimScore score = FlickAimScore.parse(elements[1]);
                    final FlickAimAccuracy accuracy = FlickAimAccuracy.parse(elements[2]);
                    final FlickAimRound round = FlickAimRound.of(score, accuracy);
                    
                    final FlickAimRecordDate date = FlickAimRecordDate.parse(elements[0]);
                    final List<FlickAimRound> rounds = records.computeIfAbsent(date, key -> new ArrayList<>());
                    
                    rounds.add(round);
                });

            this.cache.clear();
            records.forEach((date, rounds) -> {
                this.cache.put(date, FlickAimRecord.of(date, rounds));
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (FlickAimRecord records : cache.values()) {
                final FlickAimRecordDate date = records.date();
                for (FlickAimRound round : records.rounds()) {
                    writer.write(toCsvRecord(date, round) + "\n");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private String toCsvRecord(FlickAimRecordDate date, FlickAimRound round) {
        List<String> elements = List.of(
            date.value().toString(),
            String.valueOf(round.score().value()),
            String.valueOf(round.accuracy().value())
        );
        return String.join(",", elements);
    }
}
