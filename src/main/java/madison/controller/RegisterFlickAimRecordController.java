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
import madison.domain.flick.FlickAimRecord;
import madison.domain.flick.FlickAimRecordDate;
import madison.domain.flick.FlickAimRecordRepository;
import madison.domain.flick.FlickAimRound;
import madison.trial.Dialog;
import madison.util.ObjectContainer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlPath("/fxml/register-flick-aim-record.fxml")
public class RegisterFlickAimRecordController implements Initializable, InitializeStage {
    private Stage stage;
    
    @FXML
    private VBox formVBox;
    @FXML
    private Button registerButton;
    
    private final List<FlickAimRoundFormController> formControllers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanExpression validExpression = new SimpleBooleanProperty(true);
        for (int i=0; i<11; i++) {
            final Fxml<FlickAimRoundFormController, Parent> fxml = Fxml.load(FlickAimRoundFormController.class);
            final FlickAimRoundFormController controller = fxml.getController();
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

        final List<FlickAimRound> rounds = formControllers.stream().map(FlickAimRoundFormController::getFlickAimRound).collect(Collectors.toList());
        final FlickAimRecord record = FlickAimRecord.of(FlickAimRecordDate.now(), rounds);

        final FlickAimRecordRepository repository = ObjectContainer.getInstance().get(FlickAimRecordRepository.class);
        repository.register(record);

        formControllers.forEach(FlickAimRoundFormController::clear);

        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
