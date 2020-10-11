package madison.controller;

import gl.javafx.FxmlPath;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.statistic.TrackingAimStatistic;
import madison.trial.chart.ChartSeriesSetupHelper;
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

        ChartSeriesSetupHelper<String, Double> medianChartHelper = new ChartSeriesSetupHelper<>(flickAimMedianChart);
        ChartSeriesSetupHelper<String, Double> totalScoreChartHelper = new ChartSeriesSetupHelper<>(flickAimTotalScoreChart);
        
        for (FlickAimRecord record : repository.findAllOrderByDate()) {
            final FlickAimStatistic statistic = record.calculateStatistic();

            medianChartHelper.addData("flick aim median score",
                    statistic.date().value().toString(),
                    statistic.medianRound().value().score().value());
            
            totalScoreChartHelper.addData("flick aim total score",
                    statistic.date().value().toString(),
                    statistic.totalScore().value());
        }
    }

    private void setupTrackingAimChart() {
        final TrackingAimRecordRepository repository = ObjectContainer.getInstance().get(TrackingAimRecordRepository.class);

        ChartSeriesSetupHelper<String, Double> medianChartHelper = new ChartSeriesSetupHelper<>(trackingAimMedianChart);
        ChartSeriesSetupHelper<String, Double> totalScoreChartHelper = new ChartSeriesSetupHelper<>(trackingAimTotalScoreChart);

        for (TrackingAimRecord record : repository.findAllOrderByDate()) {
            final TrackingAimStatistic statistic = record.calculateStatistic();

            medianChartHelper.addData("tracking aim median score",
                    statistic.date().value().toString(),
                    statistic.medianRound().value().score().value());

            totalScoreChartHelper.addData("tracking aim total score",
                    statistic.date().value().toString(),
                    statistic.totalScore().value());
        }
    }
}
