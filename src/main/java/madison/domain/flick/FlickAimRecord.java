package madison.domain.flick;

import java.util.List;
import java.util.Objects;

public class FlickAimRecord {
    private final FlickAimRecordDate date;
    private final List<FlickAimRound> rounds;

    public static FlickAimRecord of(FlickAimRecordDate date, List<FlickAimRound> records) {
        return new FlickAimRecord(date, records);
    }
    
    private FlickAimRecord(FlickAimRecordDate date, List<FlickAimRound> rounds) {
        this.date = Objects.requireNonNull(date);
        this.rounds = Objects.requireNonNull(rounds);
    }

    public FlickAimRecordDate date() {
        return date;
    }

    public List<FlickAimRound> rounds() {
        return rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlickAimRecord that = (FlickAimRecord) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(rounds, that.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rounds);
    }

    @Override
    public String toString() {
        return "FlickAimRecords{" +
                "date=" + date +
                ", rounds=" + rounds +
                '}';
    }
}
