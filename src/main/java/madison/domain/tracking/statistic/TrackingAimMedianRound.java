package madison.domain.tracking.statistic;

import gl.util.annotation.Entity;
import madison.domain.tracking.TrackingAimRound;

import java.util.Objects;

@Entity
public class TrackingAimMedianRound {
    private final TrackingAimRound value;

    public static TrackingAimMedianRound of(TrackingAimRound value) {
        return new TrackingAimMedianRound(value);
    }
    
    private TrackingAimMedianRound(TrackingAimRound value) {
        this.value = Objects.requireNonNull(value);
    }

    public TrackingAimRound value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimMedianRound{" +
                "value=" + value +
                '}';
    }
}
