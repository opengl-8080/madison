package madison.domain.tracking;

import madison.domain.Entity;

import java.util.Objects;

@Entity
public class TrackingAimRound {
    private final TrackingAimScore score;
    private final TrackingAimAccuracy accuracy;
    private final TrackingAimDamageEff damageEff;

    public static TrackingAimRound of(TrackingAimScore score, TrackingAimAccuracy accuracy, TrackingAimDamageEff damageEff) {
        return new TrackingAimRound(score, accuracy, damageEff);
    }
    
    private TrackingAimRound(TrackingAimScore score, TrackingAimAccuracy accuracy, TrackingAimDamageEff damageEff) {
        this.score = Objects.requireNonNull(score);
        this.accuracy = Objects.requireNonNull(accuracy);
        this.damageEff = Objects.requireNonNull(damageEff);
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
                "score=" + score +
                ", accuracy=" + accuracy +
                ", damageEff=" + damageEff +
                '}';
    }
}
