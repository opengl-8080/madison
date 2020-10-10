package madison.domain.flick.statistic;

import madison.domain.Entity;
import madison.domain.flick.FlickAimRecordDate;

import java.util.Objects;

@Entity
public class FlickAimStatistic {
    private final FlickAimMedianRound medianRound;
    private final FlickAimTotalScore totalScore;

    public static FlickAimStatistic of(FlickAimMedianRound medianRound, FlickAimTotalScore totalScore) {
        return new FlickAimStatistic(medianRound, totalScore);
    }
    
    private FlickAimStatistic(FlickAimMedianRound medianRound, FlickAimTotalScore totalScore) {
        this.medianRound = Objects.requireNonNull(medianRound);
        this.totalScore = Objects.requireNonNull(totalScore);
    }

    public FlickAimRecordDate date() {
        return medianRound.value().date();
    }

    public FlickAimMedianRound medianRound() {
        return medianRound;
    }

    public FlickAimTotalScore totalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return "FlickAimStatistic{" +
                "medianRound=" + medianRound +
                ", totalScore=" + totalScore +
                '}';
    }
}
