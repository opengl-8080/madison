package madison.controller.model;

import madison.domain.flick.statistic.FlickAimStatistic;

import java.util.Objects;

public class FlickAimRecordTableModel {
    private final FlickAimStatistic previous;
    private final FlickAimStatistic statistic;

    public FlickAimRecordTableModel(FlickAimStatistic statistic) {
        this.previous = null;
        this.statistic = Objects.requireNonNull(statistic);
    }

    public FlickAimRecordTableModel(FlickAimStatistic previous, FlickAimStatistic statistic) {
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
