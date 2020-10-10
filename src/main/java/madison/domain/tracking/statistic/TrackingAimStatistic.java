package madison.domain.tracking.statistic;

import madison.domain.Entity;
import madison.domain.tracking.TrackingAimRecordDate;

import java.util.Objects;

@Entity
public class TrackingAimStatistic {
    private final TrackingAimMedianRound medianRound;
    private final TrackingAimTotalScore totalScore;

    public static TrackingAimStatistic of(TrackingAimMedianRound medianRound, TrackingAimTotalScore totalScore) {
        return new TrackingAimStatistic(medianRound, totalScore);
    }
    
    private TrackingAimStatistic(TrackingAimMedianRound medianRound, TrackingAimTotalScore totalScore) {
        this.medianRound = Objects.requireNonNull(medianRound);
        this.totalScore = Objects.requireNonNull(totalScore);
    }

    public TrackingAimRecordDate date() {
        return medianRound.value().date();
    }

    public TrackingAimMedianRound medianRound() {
        return medianRound;
    }

    public TrackingAimTotalScore totalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return "TrackingAimStatistic{" +
                "medianRound=" + medianRound +
                ", totalScore=" + totalScore +
                '}';
    }
}
