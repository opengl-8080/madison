package madison.domain.tracking.statistic;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class TrackingAimTotalScore {
    private final double value;

    public static TrackingAimTotalScore of(double value) {
        return new TrackingAimTotalScore(value);
    }
    
    private TrackingAimTotalScore(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimTotalScore{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimTotalScore that = (TrackingAimTotalScore) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
