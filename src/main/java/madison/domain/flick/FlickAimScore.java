package madison.domain.flick;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class FlickAimScore {
    private final double value;
    
    public static FlickAimScore of(double value) {
        return new FlickAimScore(value);
    }
    
    public static FlickAimScore parse(String text) {
        return of(Double.parseDouble(text));
    }

    private FlickAimScore(double value) {
        this.value = value;
    }
    
    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimScore that = (FlickAimScore) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FlickAimScore{" +
                "value=" + value +
                '}';
    }
}
