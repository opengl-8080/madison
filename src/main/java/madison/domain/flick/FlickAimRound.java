package madison.domain.flick;

import madison.domain.Entity;

import java.util.Objects;

@Entity
public class FlickAimRound {
    private final FlickAimRecordDate date;
    private final FlickAimScore score;
    private final FlickAimAccuracy accuracy;
    
    public static FlickAimRound of(FlickAimRecordDate date, FlickAimScore score, FlickAimAccuracy accuracy) {
        return new FlickAimRound(date, score, accuracy);
    }

    private FlickAimRound(FlickAimRecordDate date, FlickAimScore score, FlickAimAccuracy accuracy) {
        this.date = Objects.requireNonNull(date);
        this.score = Objects.requireNonNull(score);
        this.accuracy = Objects.requireNonNull(accuracy);
    }

    public FlickAimRecordDate date() {
        return date;
    }

    public FlickAimScore score() {
        return score;
    }

    public FlickAimAccuracy accuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "FlickAimRound{" +
                "date=" + date +
                ", score=" + score +
                ", accuracy=" + accuracy +
                '}';
    }
}
