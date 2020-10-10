package madison.domain.flick.statistic;

import madison.domain.Entity;
import madison.domain.flick.FlickAimRound;

import java.util.Objects;

@Entity
public class FlickAimMedianRound {
    private final FlickAimRound value;

    public static FlickAimMedianRound of(FlickAimRound value) {
        return new FlickAimMedianRound(value);
    }
    
    private FlickAimMedianRound(FlickAimRound value) {
        this.value = Objects.requireNonNull(value);
    }

    public FlickAimRound value() {
        return value;
    }

    @Override
    public String toString() {
        return "FlickAimMedianRound{" +
                "value=" + value +
                '}';
    }
}
