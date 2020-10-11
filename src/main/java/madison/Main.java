package madison;

import gl.javafx.FxWindow;
import gl.javafx.Fxml;
import gl.util.ObjectContainer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import madison.controller.MainController;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.persistence.flick.CsvFlickAimRecordRepository;
import madison.persistence.tracking.CsvTrackingAimRecordRepository;
import madison.util.MadisonHome;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(Main.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        final CsvFlickAimRecordRepository csvFlickAimRecordRepository = new CsvFlickAimRecordRepository(MadisonHome.getInstance().flickAimRecordCsv());
        final CsvTrackingAimRecordRepository trackingAimRecordRepository = new CsvTrackingAimRecordRepository(MadisonHome.getInstance().trackingAimRecordCsv());
        
        ObjectContainer.getInstance().register(FlickAimRecordRepository.class, csvFlickAimRecordRepository);
        ObjectContainer.getInstance().register(TrackingAimRecordRepository.class, trackingAimRecordRepository);
        
        final Fxml<MainController, Parent> fxml = Fxml.load(MainController.class);

        FxWindow.newWindow(fxml)
                .title("Madison")
                .show(primaryStage);
    }
}