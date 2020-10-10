package madison.controller;

import gl.javafx.FxmlPath;
import gl.javafx.control.ValidationTextField;
import gl.javafx.control.Validator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import madison.domain.flick.FlickAimAccuracy;
import madison.domain.flick.FlickAimRecordDate;
import madison.domain.flick.FlickAimRound;
import madison.domain.flick.FlickAimScore;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@FxmlPath("/fxml/flick-aim-round-form.fxml")
public class FlickAimRoundFormController implements Initializable {
    @FXML
    private Label numberLabel;
    @FXML
    private ValidationTextField scoreTextField;
    @FXML
    private ValidationTextField accuracyTextField;
    
    private final BooleanProperty valid = new SimpleBooleanProperty(false);
    
    public void setNumberLabel(int number) {
        this.numberLabel.setText(String.valueOf(number));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valid.bind(
            scoreTextField.validProperty().and(accuracyTextField.validProperty())
        );

        final Pattern scorePattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

        scoreTextField.addValidator(Validator.patternOf(scorePattern));
        accuracyTextField.addValidator(Validator.patternOf(scorePattern));
    }
    
    public ReadOnlyBooleanProperty validProperty() {
        return valid;
    }
    
    public FlickAimRound getFlickAimRound() {
        final FlickAimRecordDate date = FlickAimRecordDate.now();
        final FlickAimScore score = FlickAimScore.parse(scoreTextField.getText());
        final FlickAimAccuracy accuracy = FlickAimAccuracy.parse(accuracyTextField.getText());
        return FlickAimRound.of(date, score, accuracy);
    }
    
    public void clear() {
        scoreTextField.setText("");
        accuracyTextField.setText("");
    }
}
