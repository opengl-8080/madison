package madison.controller;

import gl.javafx.FxWindow;
import gl.javafx.Fxml;
import gl.javafx.FxmlPath;
import gl.javafx.InitializeStage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;

@FxmlPath("/fxml/main.fxml")
public class MainController implements InitializeStage {
    
    private Stage stage;
    
    @FXML
    public void onSelectRegisterFlickAimRecord() {
        Fxml<RegisterFlickAimRecordController, Parent> fxml = Fxml.load(RegisterFlickAimRecordController.class);
        FxWindow.newWindow(fxml)
                .title("Register Flick Aim Record")
                .modality(stage, Modality.WINDOW_MODAL)
                .resizable(false)
                .showAndWait();
    }
    
    @FXML
    public void onSelectRegisterTrackingAimRecord() {
        Fxml<RegisterTrackingAimRecordController, Parent> fxml = Fxml.load(RegisterTrackingAimRecordController.class);
        FxWindow.newWindow(fxml)
                .title("Register Tracking Aim Record")
                .modality(stage, Modality.WINDOW_MODAL)
                .resizable(false)
                .showAndWait();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
