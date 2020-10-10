package madison.controller;

import gl.javafx.Fxml;
import gl.javafx.FxmlPath;
import gl.javafx.InitializeStage;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import madison.domain.tracking.TrackingAimRecord;
import madison.domain.tracking.TrackingAimRecordDate;
import madison.domain.tracking.TrackingAimRecordRepository;
import madison.domain.tracking.TrackingAimRound;
import madison.trial.Dialog;
import madison.util.ObjectContainer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlPath("/fxml/register-tracking-aim-record.fxml")
public class RegisterTrackingAimRecordController implements Initializable, InitializeStage {
    private Stage stage;

    @FXML
    private VBox formVBox;
    @FXML
    private Button registerButton;

    private final List<TrackingAimRoundFormController> formControllers = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanExpression validExpression = new SimpleBooleanProperty(true);
        for (int i=0; i<11; i++) {
            final Fxml<TrackingAimRoundFormController, Parent> fxml = Fxml.load(TrackingAimRoundFormController.class);
            final TrackingAimRoundFormController controller = fxml.getController();
            controller.setNumberLabel(i + 1);
            formControllers.add(controller);

            formVBox.getChildren().add(fxml.getParent());

            validExpression = validExpression.and(controller.validProperty());
        }

        registerButton.disableProperty().bind(validExpression.not());
    }

    @FXML
    public void onClickRegisterButton() {
        if (Dialog.confirm("登録します。よろしいですか？").isCancel()) {
            return;
        }

        final List<TrackingAimRound> rounds = formControllers.stream().map(TrackingAimRoundFormController::getTrackingAimRound).collect(Collectors.toList());
        final TrackingAimRecord record = TrackingAimRecord.of(TrackingAimRecordDate.now(), rounds);

        final TrackingAimRecordRepository repository = ObjectContainer.getInstance().get(TrackingAimRecordRepository.class);
        repository.register(record);

        formControllers.forEach(TrackingAimRoundFormController::clear);

        stage.close();
    }
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
