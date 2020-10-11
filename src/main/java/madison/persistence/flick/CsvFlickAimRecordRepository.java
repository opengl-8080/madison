package madison.persistence.flick;

import gl.file.csv.CsvFile;
import madison.domain.flick.FlickAimAccuracy;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordDate;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.FlickAimRound;
import madison.domain.flick.FlickAimScore;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFlickAimRecordRepository implements FlickAimRecordRepository {
    private final CsvFile<CsvFlickAimRound> csv;
    private final List<FlickAimRecord> cache = new ArrayList<>();

    public CsvFlickAimRecordRepository(Path file) {
        csv = CsvFile.<CsvFlickAimRound>builder(file)
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

        for (CsvFlickAimRound round : csv.load()) {
            final List<FlickAimRound> rounds = records.computeIfAbsent(round.date, key -> new ArrayList<>());
            rounds.add(FlickAimRound.of(round.score, round.accuracy));
        }
        
        this.cache.clear();
        
        records.forEach((date, rounds) -> this.cache.add(FlickAimRecord.of(date, rounds)));

        this.cache.sort(Comparator.comparing(FlickAimRecord::date));
    }
    
    private void save() {
        List<CsvFlickAimRound> csvRounds = new ArrayList<>();
        
        for (FlickAimRecord record : cache) {
            for (FlickAimRound round : record.rounds()) {
                CsvFlickAimRound csvRound = new CsvFlickAimRound();
                
                csvRound.date = record.date();
                csvRound.score = round.score();
                csvRound.accuracy = round.accuracy();

                csvRounds.add(csvRound);
            }
        }
        
        csv.write(csvRounds);
    }
    
    private List<String> format(CsvFlickAimRound round) {
        return List.of(
            round.date.value().toString(),
            String.valueOf(round.score.value()),
            String.valueOf(round.accuracy.value())
        );
    }
    
    private CsvFlickAimRound parse(List<String> elements) {
        final CsvFlickAimRound round = new CsvFlickAimRound();

        round.date = FlickAimRecordDate.parse(elements.get(0));
        round.score = FlickAimScore.parse(elements.get(1));
        round.accuracy = FlickAimAccuracy.parse(elements.get(2));

        return round;
    }
}
