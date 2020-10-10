package madison.persistence.flick;

import madison.domain.flick.FlickAimAccuracy;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordDate;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.FlickAimRound;
import madison.domain.flick.FlickAimScore;
import madison.trial.csv.CsvFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvFlickAimRecordRepository implements FlickAimRecordRepository {
    private final CsvFile<FlickAimRound> csv;
    private final List<FlickAimRecord> cache = new ArrayList<>();

    public CsvFlickAimRecordRepository(Path file) {
        csv = CsvFile.<FlickAimRound>builder(file)
                    .delimiter("\t")
                    .formatter(this::format)
                    .parser(this::parse)
                    .build();
        load();
    }

    @Override
    public void register(FlickAimRecord record) {
        cache.add(record);
        save();
    }

    @Override
    public List<FlickAimRecord> findAllOrderByDate() {
        return List.copyOf(cache);
    }

    private void load() {
        if (!csv.exists()) {
            return;
        }
        
        final Map<FlickAimRecordDate, List<FlickAimRound>> records = new HashMap<>();

        for (FlickAimRound round : csv.load()) {
            final List<FlickAimRound> rounds = records.computeIfAbsent(round.date(), key -> new ArrayList<>());
            rounds.add(round);
        }
        
        this.cache.clear();
        
        records.values().forEach(rounds -> this.cache.add(FlickAimRecord.of(rounds)));

        this.cache.sort(Comparator.comparing(FlickAimRecord::date));
    }
    
    private void save() {
        final List<FlickAimRound> rounds = cache.stream()
                .flatMap(record -> record.rounds().stream())
                .collect(Collectors.toList());
        
        csv.write(rounds);
    }
    
    private String toCsvRecord(FlickAimRecordDate date, FlickAimRound round) {
        List<String> elements = List.of(
            date.value().toString(),
            String.valueOf(round.score().value()),
            String.valueOf(round.accuracy().value())
        );
        return String.join(",", elements);
    }
    
    private List<String> format(FlickAimRound round) {
        return List.of(
            round.date().value().toString(),
            String.valueOf(round.score().value()),
            String.valueOf(round.accuracy().value())
        );
    }
    
    private FlickAimRound parse(List<String> elements) {
        final FlickAimRecordDate date = FlickAimRecordDate.parse(elements.get(0));
        final FlickAimScore score = FlickAimScore.parse(elements.get(1));
        final FlickAimAccuracy accuracy = FlickAimAccuracy.parse(elements.get(2));
        return FlickAimRound.of(date, score, accuracy);
    }
}
