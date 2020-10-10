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
    private final List<TrackingAimRound> rounds;

    public static TrackingAimRecord of(List<TrackingAimRound> rounds) {
        return new TrackingAimRecord(rounds);
    }
    
    private TrackingAimRecord(List<TrackingAimRound> rounds) {
        this.rounds = Objects.requireNonNull(rounds);
        checkDateAreNotDuplicated(rounds);
    }
    
    private void checkDateAreNotDuplicated(List<TrackingAimRound> rounds) {
        TrackingAimRecordDate date = null;
        for (TrackingAimRound round : rounds) {
            if (date != null && !date.equals(round.date())) {
                throw new IllegalArgumentException("日付が一致していないラウンドが含まれています. date=" + round.date());
            }
            date = round.date();
        }
    }

    public TrackingAimRecordDate date() {
        return rounds.get(0).date();
    }

    public List<TrackingAimRound> rounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "TrackingAimRecord{" +
                "rounds=" + rounds +
                '}';
    }

    public TrackingAimStatistic calculateStatistic() {
        return TrackingAimStatistic.of(findMedianRound(), calculateTotalScore());
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
