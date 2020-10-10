package madison.domain.flick;

import java.util.Objects;

public class FlickAimAccuracy {
    private final double value;
    
    public static FlickAimAccuracy of(double value) {
        return new FlickAimAccuracy(value);
    }

    public static FlickAimAccuracy parse(String text) {
        return of(Double.parseDouble(text));
    }

    private FlickAimAccuracy(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "FlickAimAccuracy{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimAccuracy that = (FlickAimAccuracy) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
