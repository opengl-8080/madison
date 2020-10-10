package madison.domain.tracking;

import java.util.List;
import java.util.Objects;

public class TrackingAimRecord {
    private final TrackingAimRecordDate date;
    private final List<TrackingAimRound> rounds;

    public static TrackingAimRecord of(TrackingAimRecordDate date, List<TrackingAimRound> rounds) {
        return new TrackingAimRecord(date, rounds);
    }
    
    private TrackingAimRecord(TrackingAimRecordDate date, List<TrackingAimRound> rounds) {
        this.date = Objects.requireNonNull(date);
        this.rounds = Objects.requireNonNull(rounds);
    }

    public TrackingAimRecordDate date() {
        return date;
    }

    public List<TrackingAimRound> rounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return "TrackingAimRecord{" +
                "date=" + date +
                ", rounds=" + rounds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackingAimRecord that = (TrackingAimRecord) o;
        return date.equals(that.date) &&
                rounds.equals(that.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rounds);
    }
}
