package madison.domain.tracking;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimRound that = (TrackingAimRound) o;
        return score.equals(that.score) &&
                accuracy.equals(that.accuracy) &&
                damageEff.equals(that.damageEff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, accuracy, damageEff);
    }
}
