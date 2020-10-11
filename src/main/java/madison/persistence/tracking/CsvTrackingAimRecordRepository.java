package madison.persistence.tracking;

import gl.file.csv.CsvFile;
import madison.domain.tracking.TrackingAimAccuracy;
import madison.domain.tracking.TrackingAimDamageEff;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordDate;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.TrackingAimRound;
import madison.domain.tracking.TrackingAimScore;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvTrackingAimRecordRepository implements TrackingAimRecordRepository {
    private final CsvFile<CsvTrackingAimRound> csv;
    private final List<TrackingAimRecord> cache = new ArrayList<>();

    public CsvTrackingAimRecordRepository(Path file) {
        this.csv = CsvFile.<CsvTrackingAimRound>builder(file)
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

        for (CsvTrackingAimRound round : csv.load()) {
            final List<TrackingAimRound> rounds = records.computeIfAbsent(round.date, key -> new ArrayList<>());
            rounds.add(TrackingAimRound.of(round.score, round.accuracy, round.damageEff));
        }

        cache.clear();
        
        records.forEach((date, rounds) -> cache.add(TrackingAimRecord.of(date, rounds)));
        
        cache.sort(Comparator.comparing(TrackingAimRecord::date));
    }

    private void save() {
        List<CsvTrackingAimRound> csvRounds = new ArrayList<>();
        
        for (TrackingAimRecord record : cache) {
            for (TrackingAimRound round : record.rounds()) {
                CsvTrackingAimRound csvRound = new CsvTrackingAimRound();

                csvRound.date = record.date();
                csvRound.score = round.score();
                csvRound.accuracy = round.accuracy();
                csvRound.damageEff = round.damageEff();
                
                csvRounds.add(csvRound);
            }
        }
        
        csv.write(csvRounds);
    }

    private List<String> format(CsvTrackingAimRound round) {
        return List.of(
            round.date.value().toString(),
            String.valueOf(round.score.value()),
            String.valueOf(round.accuracy.value()),
            String.valueOf(round.damageEff.value())
        );
    }
    
    private CsvTrackingAimRound parse(List<String> elements) {
        CsvTrackingAimRound round = new CsvTrackingAimRound();

        round.date = TrackingAimRecordDate.parse(elements.get(0));
        round.score = TrackingAimScore.parse(elements.get(1));
        round.accuracy = TrackingAimAccuracy.parse(elements.get(2));
        round.damageEff = TrackingAimDamageEff.parse(elements.get(3));
        
        return round;
    }
}
