package madison.domain.flick;

import gl.util.annotation.Entity;
import madison.domain.flick.statistic.FlickAimMedianRound;
import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.flick.statistic.FlickAimTotalScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
public class FlickAimRecord {
    private final FlickAimRecordDate date;
    private final List<FlickAimRound> rounds;

    public static FlickAimRecord of(FlickAimRecordDate date, List<FlickAimRound> records) {
        return new FlickAimRecord(date, records);
    }
    
    private FlickAimRecord(FlickAimRecordDate date, List<FlickAimRound> rounds) {
        this.date = Objects.requireNonNull(date);
        this.rounds = Objects.requireNonNull(rounds);
    }

    public FlickAimRecordDate date() {
        return date;
    }

    public List<FlickAimRound> rounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "FlickAimRecord{" +
                "date=" + date +
                ", rounds=" + rounds +
                '}';
    }

    public FlickAimStatistic calculateStatistic() {
        return FlickAimStatistic.of(
            date,
            findMedianRound(),
            calculateTotalScore()
        );
    }

    private FlickAimMedianRound findMedianRound() {
        final List<FlickAimRound> copy = new ArrayList<>(rounds);
        copy.sort(Comparator.comparingDouble(round -> round.score().value()));
        final FlickAimRound median = copy.get(copy.size() / 2);
        return FlickAimMedianRound.of(median);
    }
    
    private FlickAimTotalScore calculateTotalScore() {
        final double total = rounds.stream().mapToDouble(round -> round.score().value()).sum();
        return FlickAimTotalScore.of(total);
    }
}
