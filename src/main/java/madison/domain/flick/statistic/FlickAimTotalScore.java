package madison.domain.flick.statistic;

import gl.util.annotation.ValueObject;

import java.util.Objects;

@ValueObject
public class FlickAimTotalScore {
    private final double value;

    public static FlickAimTotalScore of(double value) {
        return new FlickAimTotalScore(value);
    }
    
    private FlickAimTotalScore(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return "FlickAimTotalScore{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimTotalScore that = (FlickAimTotalScore) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
