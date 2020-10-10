package madison.persistence.tracking;

import madison.domain.tracking.TrackingAimAccuracy;
import madison.domain.tracking.TrackingAimDamageEff;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordDate;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.TrackingAimRound;
import madison.domain.tracking.TrackingAimScore;
import madison.trial.csv.CsvFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvTrackingAimRecordRepository implements TrackingAimRecordRepository {
    private final CsvFile<TrackingAimRound> csv;
    private final List<TrackingAimRecord> cache = new ArrayList<>();

    public CsvTrackingAimRecordRepository(Path file) {
        this.csv = CsvFile.<TrackingAimRound>builder(file)
                        .delimiter("\t")
                        .formatter(this::format)
                        .parser(this::parse)
                        .build();
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
        if (!csv.exists()) {
            return;
        }

        final Map<TrackingAimRecordDate, List<TrackingAimRound>> records = new HashMap<>();
        for (TrackingAimRound round : csv.load()) {
            final List<TrackingAimRound> rounds = records.computeIfAbsent(round.date(), key -> new ArrayList<>());
            rounds.add(round);
        }

        cache.clear();
        
        records.values().forEach(rounds -> cache.add(TrackingAimRecord.of(rounds)));
        
        cache.sort(Comparator.comparing(TrackingAimRecord::date));
    }

    private void save() {
        final List<TrackingAimRound> rounds = cache.stream()
                .flatMap(record -> record.rounds().stream())
                .collect(Collectors.toList());
        
        csv.write(rounds);
    }

    private List<String> format(TrackingAimRound round) {
        return List.of(
            round.date().value().toString(),
            String.valueOf(round.score().value()),
            String.valueOf(round.accuracy().value()),
            String.valueOf(round.damageEff().value())
        );
    }
    
    private TrackingAimRound parse(List<String> elements) {
        final TrackingAimRecordDate date = TrackingAimRecordDate.parse(elements.get(0));
        final TrackingAimScore score = TrackingAimScore.parse(elements.get(1));
        final TrackingAimAccuracy accuracy = TrackingAimAccuracy.parse(elements.get(2));
        final TrackingAimDamageEff damageEff = TrackingAimDamageEff.parse(elements.get(3));
        return TrackingAimRound.of(date, score, accuracy, damageEff);
    }
}
