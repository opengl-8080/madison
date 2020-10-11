package madison.domain.tracking;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class TrackingAimDamageEff {
    private final double value;

    public static TrackingAimDamageEff of(double value) {
        return new TrackingAimDamageEff(value);
    }

    public static TrackingAimDamageEff parse(String text) {
        return of(Double.parseDouble(text));
    }
    
    private TrackingAimDamageEff(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimDamageEff{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimDamageEff that = (TrackingAimDamageEff) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
