package madison.domain.tracking;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class TrackingAimAccuracy {
    private final double value;

    public static TrackingAimAccuracy of(double value) {
        return new TrackingAimAccuracy(value);
    }

    public static TrackingAimAccuracy parse(String text) {
        return of(Double.parseDouble(text));
    }
    
    private TrackingAimAccuracy(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimAccuracy{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimAccuracy that = (TrackingAimAccuracy) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
