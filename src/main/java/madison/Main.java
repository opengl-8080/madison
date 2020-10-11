package madison;

import gl.javafx.FxWindow;
import gl.javafx.Fxml;
import gl.util.ObjectContainer;
import gl.util.config.CompositeConfigResolver;
import gl.util.config.ConfigResolver;
import gl.util.config.EnvironmentVariableConfigResolver;
import gl.util.config.SystemPropertyConfigResolver;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import madison.controller.MainController;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.persistence.flick.CsvFlickAimRecordRepository;
import madison.persistence.tracking.CsvTrackingAimRecordRepository;
import madison.util.MadisonHome;

import java.util.List;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(Main.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        final ConfigResolver resolver = new CompositeConfigResolver(
                List.of(new EnvironmentVariableConfigResolver(), new SystemPropertyConfigResolver())
        );

        final MadisonHome madisonHome = new MadisonHome(resolver);

        final CsvFlickAimRecordRepository csvFlickAimRecordRepository = new CsvFlickAimRecordRepository(madisonHome.flickAimRecordCsv());
        final CsvTrackingAimRecordRepository trackingAimRecordRepository = new CsvTrackingAimRecordRepository(madisonHome.trackingAimRecordCsv());
        
        ObjectContainer.getInstance().register(FlickAimRecordRepository.class, csvFlickAimRecordRepository);
        ObjectContainer.getInstance().register(TrackingAimRecordRepository.class, trackingAimRecordRepository);
        
        final Fxml<MainController, Parent> fxml = Fxml.load(MainController.class);

        FxWindow.newWindow(fxml)
                .title("Madison")
                .show(primaryStage);
    }
}