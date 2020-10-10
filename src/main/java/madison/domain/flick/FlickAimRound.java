package madison.domain.flick;

import java.util.Objects;

public class FlickAimRound {
    private final FlickAimScore score;
    private final FlickAimAccuracy accuracy;
    
    public static FlickAimRound of(FlickAimScore score, FlickAimAccuracy accuracy) {
        return new FlickAimRound(score, accuracy);
    }

    private FlickAimRound(FlickAimScore score, FlickAimAccuracy accuracy) {
        this.score = Objects.requireNonNull(score);
        this.accuracy = Objects.requireNonNull(accuracy);
    }

    public FlickAimScore score() {
        return score;
    }

    public FlickAimAccuracy accuracy() {
        return accuracy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimRound that = (FlickAimRound) o;
        return Objects.equals(score, that.score) &&
                Objects.equals(accuracy, that.accuracy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, accuracy);
    }

    @Override
    public String toString() {
        return "FlickAimRecord{" +
                "score=" + score +
                ", accuracy=" + accuracy +
                '}';
    }
}
