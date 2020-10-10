package madison.domain.tracking;

import madison.domain.Entity;
import madison.domain.tracking.statistic.TrackingAimMedianRound;
import madison.domain.tracking.statistic.TrackingAimStatistic;
import madison.domain.tracking.statistic.TrackingAimTotalScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
public class TrackingAimRecord {
    private final TrackingAimRecordDate date;
    private final List<TrackingAimRound> rounds;

    public static TrackingAimRecord of(TrackingAimRecordDate date, List<TrackingAimRound> rounds) {
        return new TrackingAimRecord(date, rounds);
    }
    
    private TrackingAimRecord(TrackingAimRecordDate date, List<TrackingAimRound> rounds) {
        this.date = Objects.requireNonNull(date);
        this.rounds = Objects.requireNonNull(rounds);
    }

    public TrackingAimRecordDate date() {
        return date;
    }

    public List<TrackingAimRound> rounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "TrackingAimRecord{" +
                "date=" + date +
                ", rounds=" + rounds +
                '}';
    }

    public TrackingAimStatistic calculateStatistic() {
        return TrackingAimStatistic.of(date, findMedianRound(), calculateTotalScore());
    }
    
    private TrackingAimMedianRound findMedianRound() {
        final List<TrackingAimRound> copy = new ArrayList<>(rounds);
        copy.sort(Comparator.comparingDouble(round -> round.score().value()));
        final TrackingAimRound median = copy.get(copy.size() / 2);
        return TrackingAimMedianRound.of(median);
    }
    
    private TrackingAimTotalScore calculateTotalScore() {
        final double total = rounds.stream().mapToDouble(round -> round.score().value()).sum();
        return TrackingAimTotalScore.of(total);
    }
}
