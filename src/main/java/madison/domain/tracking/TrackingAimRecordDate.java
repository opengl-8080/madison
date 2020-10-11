package madison.domain.tracking;

import gl.util.annotation.ValueObject;

import java.time.LocalDate;
import java.util.Objects;

@ValueObject
public class TrackingAimRecordDate implements Comparable<TrackingAimRecordDate> {
    private final LocalDate value;

    public static TrackingAimRecordDate of(LocalDate value) {
        return new TrackingAimRecordDate(value);
    }

    public static TrackingAimRecordDate now() {
        return of(LocalDate.now());
    }

    public static TrackingAimRecordDate parse(String text) {
        return of(LocalDate.parse(text));
    }

    private TrackingAimRecordDate(LocalDate value) {
        this.value = Objects.requireNonNull(value);
    }

    public LocalDate value() {
        return value;
    }

    @Override
    public String toString() {
        return "TrackingAimRecordDate{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimRecordDate that = (TrackingAimRecordDate) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(TrackingAimRecordDate other) {
        return value.compareTo(other.value);
    }
}
