package madison.domain.tracking;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class TrackingAimScore {
    private final double value;

    public static TrackingAimScore of(double value) {
        return new TrackingAimScore(value);
    }

    public static TrackingAimScore parse(String text) {
        return of(Double.parseDouble(text));
    }
    
    private TrackingAimScore(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimScore{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimScore that = (TrackingAimScore) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
