package madison.domain.flick;

import gl.util.annotation.Entity;

import java.util.Objects;

@Entity
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
    public String toString() {
        return "FlickAimRound{" +
                "score=" + score +
                ", accuracy=" + accuracy +
                '}';
    }
}
