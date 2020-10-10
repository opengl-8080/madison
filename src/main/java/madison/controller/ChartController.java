package madison.controller;

import gl.javafx.FxmlPath;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.statistic.TrackingAimStatistic;
import madison.util.ObjectContainer;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlPath("/fxml/chart.fxml")
public class ChartController implements Initializable {
    
    @FXML
    private LineChart<String, Double> flickAimMedianChart;
    @FXML
    private LineChart<String, Double> flickAimTotalScoreChart;
    @FXML
    private LineChart<String, Double> trackingAimMedianChart;
    @FXML
    private LineChart<String, Double> trackingAimTotalScoreChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFlickAimChart();
        setupTrackingAimChart();
    }
    
    private void setupFlickAimChart() {
        final FlickAimRecordRepository repository = ObjectContainer.getInstance().get(FlickAimRecordRepository.class);

        final XYChart.Series<String, Double> medianSeries = new XYChart.Series<>();
        final XYChart.Series<String, Double> totalScoreSeries = new XYChart.Series<>();

        medianSeries.setName("flick aim median score");
        totalScoreSeries.setName("flick aim total score");
        for (FlickAimRecord record : repository.findAllOrderByDate()) {
            final FlickAimStatistic statistic = record.calculateStatistic();

            XYChart.Data<String, Double> medianSeriesData = new XYChart.Data<>(
                statistic.date().value().toString(),
                statistic.medianRound().value().score().value()
            );
            medianSeries.getData().add(medianSeriesData);

            XYChart.Data<String, Double> totalSeriesData = new XYChart.Data<>(
                statistic.date().value().toString(),
                statistic.totalScore().value()
            );
            totalScoreSeries.getData().add(totalSeriesData);
        }

        flickAimMedianChart.getData().add(medianSeries);
        flickAimTotalScoreChart.getData().add(totalScoreSeries);
    }

    private void setupTrackingAimChart() {
        final TrackingAimRecordRepository repository = ObjectContainer.getInstance().get(TrackingAimRecordRepository.class);

        final XYChart.Series<String, Double> medianSeries = new XYChart.Series<>();
        final XYChart.Series<String, Double> totalScoreSeries = new XYChart.Series<>();

        medianSeries.setName("tracking aim median score");
        totalScoreSeries.setName("tracking aim total score");
        for (TrackingAimRecord record : repository.findAllOrderByDate()) {
            final TrackingAimStatistic statistic = record.calculateStatistic();

            XYChart.Data<String, Double> medianSeriesData = new XYChart.Data<>(
                    statistic.date().value().toString(),
                    statistic.medianRound().value().score().value()
            );
            medianSeries.getData().add(medianSeriesData);

            XYChart.Data<String, Double> totalSeriesData = new XYChart.Data<>(
                    statistic.date().value().toString(),
                    statistic.totalScore().value()
            );
            totalScoreSeries.getData().add(totalSeriesData);
        }

        trackingAimMedianChart.getData().add(medianSeries);
        trackingAimTotalScoreChart.getData().add(totalScoreSeries);
    }
}
