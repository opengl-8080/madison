package madison.domain.flick;

import madison.domain.Entity;
import madison.domain.flick.statistic.FlickAimMedianRound;
import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.flick.statistic.FlickAimTotalScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
public class FlickAimRecord {
    private final List<FlickAimRound> rounds;

    public static FlickAimRecord of(List<FlickAimRound> records) {
        return new FlickAimRecord(records);
    }
    
    private FlickAimRecord(List<FlickAimRound> rounds) {
        this.rounds = Objects.requireNonNull(rounds);
        checkDateAreNotDuplicated(rounds);
    }

    private void checkDateAreNotDuplicated(List<FlickAimRound> rounds) {
        FlickAimRecordDate date = null;
        for (FlickAimRound round : rounds) {
            if (date != null && !date.equals(round.date())) {
                throw new IllegalArgumentException("日付が一致していないラウンドが含まれています. date=" + round.date());
            }
            date = round.date();
        }
    }

    public FlickAimRecordDate date() {
        return rounds.get(0).date();
    }

    public List<FlickAimRound> rounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "FlickAimRecord{" +
                "rounds=" + rounds +
                '}';
    }

    public FlickAimStatistic calculateStatistic() {
        return FlickAimStatistic.of(findMedianRound(), calculateTotalScore());
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
