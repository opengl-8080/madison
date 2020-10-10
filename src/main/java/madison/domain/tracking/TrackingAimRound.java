package madison.domain.tracking;

import madison.domain.Entity;

import java.util.Objects;

@Entity
public class TrackingAimRound {
    private final TrackingAimRecordDate date;
    private final TrackingAimScore score;
    private final TrackingAimAccuracy accuracy;
    private final TrackingAimDamageEff damageEff;

    public static TrackingAimRound of(TrackingAimRecordDate date, TrackingAimScore score, TrackingAimAccuracy accuracy, TrackingAimDamageEff damageEff) {
        return new TrackingAimRound(date, score, accuracy, damageEff);
    }
    
    private TrackingAimRound(TrackingAimRecordDate date, TrackingAimScore score, TrackingAimAccuracy accuracy, TrackingAimDamageEff damageEff) {
        this.date = Objects.requireNonNull(date);
        this.score = Objects.requireNonNull(score);
        this.accuracy = Objects.requireNonNull(accuracy);
        this.damageEff = Objects.requireNonNull(damageEff);
    }

    public TrackingAimRecordDate date() {
        return date;
    }

    public TrackingAimScore score() {
        return score;
    }

    public TrackingAimAccuracy accuracy() {
        return accuracy;
    }

    public TrackingAimDamageEff damageEff() {
        return damageEff;
    }

    @Override
    public String toString() {
        return "TrackingAimRound{" +
                "date=" + date +
                ", score=" + score +
                ", accuracy=" + accuracy +
                ", damageEff=" + damageEff +
                '}';
    }
}
