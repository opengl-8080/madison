package madison.domain.tracking.statistic;

import gl.util.annotation.Entity;
import madison.domain.tracking.TrackingAimRecordDate;

import java.util.Objects;

@Entity
public class TrackingAimStatistic {
    private final TrackingAimRecordDate date;
    private final TrackingAimMedianRound medianRound;
    private final TrackingAimTotalScore totalScore;

    public static TrackingAimStatistic of(TrackingAimRecordDate date, TrackingAimMedianRound medianRound, TrackingAimTotalScore totalScore) {
        return new TrackingAimStatistic(date, medianRound, totalScore);
    }
    
    private TrackingAimStatistic(TrackingAimRecordDate date, TrackingAimMedianRound medianRound, TrackingAimTotalScore totalScore) {
        this.date = Objects.requireNonNull(date);
        this.medianRound = Objects.requireNonNull(medianRound);
        this.totalScore = Objects.requireNonNull(totalScore);
    }

    public TrackingAimRecordDate date() {
        return date;
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
