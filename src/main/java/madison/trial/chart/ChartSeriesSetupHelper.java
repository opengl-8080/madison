package madison.trial.chart;

import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChartSeriesSetupHelper<X, Y> {
    private final XYChart<X,Y> chart;
    private final Map<String, XYChart.Series<X, Y>> seriesMap = new HashMap<>();

    public ChartSeriesSetupHelper(XYChart<X, Y> chart) {
        this.chart = Objects.requireNonNull(chart);
    }
    
    public void addData(String name, X x, Y y) {
        final XYChart.Series<X, Y> series = seriesMap.computeIfAbsent(name, key -> {
            XYChart.Series<X, Y> newSeries = new XYChart.Series<>();
            chart.getData().add(newSeries);
            newSeries.setName(name);
            return newSeries;
        });

        series.getData().add(new XYChart.Data<>(x, y));
    }
}
