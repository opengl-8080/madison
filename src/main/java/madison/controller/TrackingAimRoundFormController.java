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
import madison.domain.tracking.TrackingAimAccuracy;
import madison.domain.tracking.TrackingAimDamageEff;
import madison.domain.tracking.TrackingAimRecordDate;
import madison.domain.tracking.TrackingAimRound;
import madison.domain.tracking.TrackingAimScore;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@FxmlPath("/fxml/tracking-aim-round-form.fxml")
public class TrackingAimRoundFormController implements Initializable {
    @FXML
    private Label numberLabel;
    @FXML
    private ValidationTextField scoreTextField;
    @FXML
    private ValidationTextField accuracyTextField;
    @FXML
    private ValidationTextField damageEffTextField;

    private final BooleanProperty valid = new SimpleBooleanProperty(false);

    public void setNumberLabel(int number) {
        this.numberLabel.setText(String.valueOf(number));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valid.bind(
            scoreTextField.validProperty().and(accuracyTextField.validProperty()).and(damageEffTextField.validProperty())
        );

        final Pattern scorePattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

        scoreTextField.addValidator(Validator.patternOf(scorePattern));
        accuracyTextField.addValidator(Validator.patternOf(scorePattern));
        damageEffTextField.addValidator(Validator.patternOf(scorePattern));
    }
    
    public ReadOnlyBooleanProperty validProperty() {
        return valid;
    }

    public TrackingAimRound getTrackingAimRound() {
        final TrackingAimRecordDate date = TrackingAimRecordDate.now();
        final TrackingAimScore score = TrackingAimScore.parse(scoreTextField.getText());
        final TrackingAimAccuracy accuracy = TrackingAimAccuracy.parse(accuracyTextField.getText());
        final TrackingAimDamageEff damageEff = TrackingAimDamageEff.parse(damageEffTextField.getText());
        return TrackingAimRound.of(date, score, accuracy, damageEff);
    }

    public void clear() {
        scoreTextField.setText("");
        accuracyTextField.setText("");
        damageEffTextField.setText("");
    }
}
