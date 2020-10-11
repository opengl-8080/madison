package madison.controller;

import gl.javafx.FxWindow;
import gl.javafx.Fxml;
import gl.javafx.FxmlPath;
import gl.javafx.InitializeStage;
import gl.javafx.control.TableViewUtil;
import gl.util.ObjectContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import madison.controller.model.FlickAimRecordTableModel;
import madison.controller.model.TrackingAimRecordTableModel;
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.statistic.TrackingAimStatistic;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

@FxmlPath("/fxml/main.fxml")
public class MainController implements InitializeStage, Initializable {
    
    private Stage stage;
    
    @FXML
    private TableView<FlickAimRecordTableModel> flickAimRecordTable;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordDateColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordScoreColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordScoreDiffColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordAccuracyColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordAccuracyDiffColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordTotalColumn;
    @FXML
    private TableColumn<FlickAimRecordTableModel, String> flickAimRecordTotalDiffColumn;

    @FXML
    private TableView<TrackingAimRecordTableModel> trackingAimRecordTable;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordDateColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordScoreColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordScoreDiffColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordAccuracyColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordAccuracyDiffColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordDamageEffColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordDamageEffDiffColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordTotalScoreColumn;
    @FXML
    private TableColumn<TrackingAimRecordTableModel, String> trackingAimRecordTotalScoreDiffColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableViewUtil.setupCellValueFactory(flickAimRecordDateColumn, FlickAimRecordTableModel::getDate);
        TableViewUtil.setupCellValueFactory(flickAimRecordScoreColumn, FlickAimRecordTableModel::getScore);
        TableViewUtil.setupCellValueFactory(flickAimRecordScoreDiffColumn, FlickAimRecordTableModel::getScoreDiff);
        TableViewUtil.setupCellValueFactory(flickAimRecordAccuracyColumn, FlickAimRecordTableModel::getAccuracy);
        TableViewUtil.setupCellValueFactory(flickAimRecordAccuracyDiffColumn, FlickAimRecordTableModel::getAccuracyDiff);
        TableViewUtil.setupCellValueFactory(flickAimRecordTotalColumn, FlickAimRecordTableModel::getTotalScore);
        TableViewUtil.setupCellValueFactory(flickAimRecordTotalDiffColumn, FlickAimRecordTableModel::getTotalScoreDiff);

        TableViewUtil.setupCellValueFactory(trackingAimRecordDateColumn, TrackingAimRecordTableModel::getDate);
        TableViewUtil.setupCellValueFactory(trackingAimRecordScoreColumn, TrackingAimRecordTableModel::getScore);
        TableViewUtil.setupCellValueFactory(trackingAimRecordScoreDiffColumn, TrackingAimRecordTableModel::getScoreDiff);
        TableViewUtil.setupCellValueFactory(trackingAimRecordAccuracyColumn, TrackingAimRecordTableModel::getAccuracy);
        TableViewUtil.setupCellValueFactory(trackingAimRecordAccuracyDiffColumn, TrackingAimRecordTableModel::getAccuracyDiff);
        TableViewUtil.setupCellValueFactory(trackingAimRecordDamageEffColumn, TrackingAimRecordTableModel::getDamageEff);
        TableViewUtil.setupCellValueFactory(trackingAimRecordDamageEffDiffColumn, TrackingAimRecordTableModel::getDamageEffDiff);
        TableViewUtil.setupCellValueFactory(trackingAimRecordTotalScoreColumn, TrackingAimRecordTableModel::getTotalScore);
        TableViewUtil.setupCellValueFactory(trackingAimRecordTotalScoreDiffColumn, TrackingAimRecordTableModel::getTotalScoreDiff);
        
        refreshFlickAimRecordTable();
        refreshTrackingAimRecordTable();
    }
    
    private void refreshFlickAimRecordTable() {
        final FlickAimRecordRepository repository = ObjectContainer.getInstance().get(FlickAimRecordRepository.class);
        final List<FlickAimRecord> flickAimRecords = repository.findAllOrderByDate();
        
        List<FlickAimRecordTableModel> modelList = new ArrayList<>();
        FlickAimStatistic previous = null;
        for (FlickAimRecord record : flickAimRecords) {
            final FlickAimStatistic statistic = record.calculateStatistic();
            if (previous == null) {
                modelList.add(new FlickAimRecordTableModel(statistic));
            } else {
                modelList.add(new FlickAimRecordTableModel(previous, statistic));
            }

            previous = statistic;
        }

        Collections.reverse(modelList);
        flickAimRecordTable.setItems(FXCollections.observableList(modelList));
    }
    
    private void refreshTrackingAimRecordTable() {
        final TrackingAimRecordRepository repository = ObjectContainer.getInstance().get(TrackingAimRecordRepository.class);
        final List<TrackingAimRecord> records = repository.findAllOrderByDate();

        List<TrackingAimRecordTableModel> modelList = new ArrayList<>();
        TrackingAimStatistic previous = null;
        for (TrackingAimRecord record : records) {
            final TrackingAimStatistic statistic = record.calculateStatistic();
            if (previous == null) {
                modelList.add(new TrackingAimRecordTableModel(statistic));
            } else {
                modelList.add(new TrackingAimRecordTableModel(previous, statistic));
            }

            previous = statistic;
        }

        Collections.reverse(modelList);
        trackingAimRecordTable.setItems(FXCollections.observableList(modelList));
    }
    
    @FXML
    public void onSelectRegisterFlickAimRecord() {
        Fxml<RegisterFlickAimRecordController, Parent> fxml = Fxml.load(RegisterFlickAimRecordController.class);
        FxWindow.newWindow(fxml)
                .title("Register Flick Aim Record")
                .modality(stage, Modality.WINDOW_MODAL)
                .resizable(false)
                .showAndWait();
        
        refreshFlickAimRecordTable();
    }
    
    @FXML
    public void onSelectRegisterTrackingAimRecord() {
        Fxml<RegisterTrackingAimRecordController, Parent> fxml = Fxml.load(RegisterTrackingAimRecordController.class);
        FxWindow.newWindow(fxml)
                .title("Register Tracking Aim Record")
                .modality(stage, Modality.WINDOW_MODAL)
                .resizable(false)
                .showAndWait();

        refreshTrackingAimRecordTable();
    }
    
    @FXML
    public void onClickShowChart() {
        final Fxml<ChartController, Parent> fxml = Fxml.load(ChartController.class);
        FxWindow.newWindow(fxml)
                .title("Chart")
                .modality(stage, Modality.NONE)
                .show();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
