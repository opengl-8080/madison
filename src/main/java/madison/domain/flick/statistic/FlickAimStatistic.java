package madison.domain.flick.statistic;

import madison.domain.Entity;
import madison.domain.flick.FlickAimRecordDate;

import java.util.Objects;

@Entity
public class FlickAimStatistic {
    private final FlickAimRecordDate date;
    private final FlickAimMedianRound medianRound;
    private final FlickAimTotalScore totalScore;

    public static FlickAimStatistic of(FlickAimRecordDate date, FlickAimMedianRound medianRound, FlickAimTotalScore totalScore) {
        return new FlickAimStatistic(date, medianRound, totalScore);
    }
    
    private FlickAimStatistic(FlickAimRecordDate date, FlickAimMedianRound medianRound, FlickAimTotalScore totalScore) {
        this.date = Objects.requireNonNull(date);
        this.medianRound = Objects.requireNonNull(medianRound);
        this.totalScore = Objects.requireNonNull(totalScore);
    }

    public FlickAimRecordDate date() {
        return date;
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
                "date=" + date +
                ", medianRound=" + medianRound +
                ", totalScore=" + totalScore +
                '}';
    }
}
