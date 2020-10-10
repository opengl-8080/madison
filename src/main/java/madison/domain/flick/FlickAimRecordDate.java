package madison.domain.flick;

import java.time.LocalDate;
import java.util.Objects;

public class FlickAimRecordDate {
    private final LocalDate value;
    
    public static FlickAimRecordDate now() {
        return of(LocalDate.now());
    }
    
    public static FlickAimRecordDate of(LocalDate value) {
        return new FlickAimRecordDate(value);
    }
    
    public static FlickAimRecordDate parse(String text) {
        return of(LocalDate.parse(text));
    }

    private FlickAimRecordDate(LocalDate value) {
        this.value = Objects.requireNonNull(value);
    }
    
    public LocalDate value() {
        return value;
    }

    @Override
    public String toString() {
        return "FlickAimRecordDate{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimRecordDate that = (FlickAimRecordDate) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
