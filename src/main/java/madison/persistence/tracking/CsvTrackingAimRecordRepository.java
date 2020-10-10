package madison.persistence.tracking;

import madison.domain.tracking.TrackingAimAccuracy;
import madison.domain.tracking.TrackingAimDamageEff;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordDate;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.TrackingAimRound;
import madison.domain.tracking.TrackingAimScore;

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

public class CsvTrackingAimRecordRepository implements TrackingAimRecordRepository {
    private final Path file;
    private final List<TrackingAimRecord> cache = new ArrayList<>();

    public CsvTrackingAimRecordRepository(Path file) {
        this.file = Objects.requireNonNull(file);
        load();
    }

    @Override
    public void register(TrackingAimRecord record) {
        cache.add(record);
        save();
    }

    @Override
    public List<TrackingAimRecord> findAllOrderByDate() {
        return List.copyOf(cache);
    }

    private void load() {
        if (!(Files.exists(file) && Files.isRegularFile(file))) {
            return;
        }

        try {
            final Map<TrackingAimRecordDate, List<TrackingAimRound>> records = new HashMap<>();
            Files.lines(file, StandardCharsets.UTF_8)
                    .forEach(line -> {
                        final String[] elements = line.split(",");

                        final TrackingAimScore score = TrackingAimScore.parse(elements[1]);
                        final TrackingAimAccuracy accuracy = TrackingAimAccuracy.parse(elements[2]);
                        final TrackingAimDamageEff damageEff = TrackingAimDamageEff.parse(elements[3]);
                        final TrackingAimRound round = TrackingAimRound.of(score, accuracy, damageEff);

                        final TrackingAimRecordDate date = TrackingAimRecordDate.parse(elements[0]);
                        final List<TrackingAimRound> rounds = records.computeIfAbsent(date, key -> new ArrayList<>());

                        rounds.add(round);
                    });

            this.cache.clear();
            records.forEach((date, rounds) -> {
                this.cache.add(TrackingAimRecord.of(date, rounds));
            });
            
            this.cache.sort(Comparator.comparing(TrackingAimRecord::date));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (TrackingAimRecord records : cache) {
                final TrackingAimRecordDate date = records.date();
                for (TrackingAimRound round : records.rounds()) {
                    writer.write(toCsvRecord(date, round) + "\n");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String toCsvRecord(TrackingAimRecordDate date, TrackingAimRound round) {
        List<String> elements = List.of(
                date.value().toString(),
                String.valueOf(round.score().value()),
                String.valueOf(round.accuracy().value()),
                String.valueOf(round.damageEff().value())
        );
        return String.join(",", elements);
    }
}
