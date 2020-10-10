package madison.controller.model;

import madison.domain.tracking.statistic.TrackingAimStatistic;

import java.util.Objects;

public class TrackingAimRecordTableModel {
    private final TrackingAimStatistic previous;
    private final TrackingAimStatistic statistic;

    public TrackingAimRecordTableModel(TrackingAimStatistic statistic) {
        this.previous = null;
        this.statistic = Objects.requireNonNull(statistic);
    }

    public TrackingAimRecordTableModel(TrackingAimStatistic previous, TrackingAimStatistic statistic) {
        this.previous = Objects.requireNonNull(previous);
        this.statistic = Objects.requireNonNull(statistic);
    }

    public String getDate() {
        return statistic.date().value().toString();
    }

    public String getScore() {
        return formatDouble(statistic.medianRound().value().score().value());
    }

    public String getScoreDiff() {
        if (previous == null) {
            return "-";
        }
        return formatDouble(statistic.medianRound().value().score().value() - previous.medianRound().value().score().value());
    }

    public String getAccuracy() {
        return formatDouble(statistic.medianRound().value().accuracy().value());
    }

    public String getAccuracyDiff() {
        if (previous == null) {
            return "-";
        }
        return formatDouble(statistic.medianRound().value().accuracy().value() - previous.medianRound().value().accuracy().value());
    }

    public String getDamageEff() {
        return formatDouble(statistic.medianRound().value().damageEff().value());
    }

    public String getDamageEffDiff() {
        if (previous == null) {
            return "-";
        }
        return formatDouble(statistic.medianRound().value().damageEff().value() - previous.medianRound().value().damageEff().value());
    }

    public String getTotalScore() {
        return formatDouble(statistic.totalScore().value());
    }

    public String getTotalScoreDiff() {
        if (previous == null) {
            return "-";
        }

        return formatDouble(statistic.totalScore().value() - previous.totalScore().value());
    }

    private String formatDouble(double value) {
        return String.format("%.2f", value);
    }
}
