package madison.trial;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Dialog {
    
    public static DialogResult confirm(String message) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setHeaderText(null);
        final Boolean ok = alert.showAndWait().map(Dialog::isOk).orElse(false);
        return new DialogResult(ok);
    }
    
    private static boolean isOk(ButtonType type) {
        final ButtonBar.ButtonData data = type.getButtonData();
        return ButtonBar.ButtonData.OK_DONE.equals(data)
                || ButtonBar.ButtonData.YES.equals(data);
    }
    
    private final String message;
    
    private Dialog(String message) {
        this.message = message;
    }
}
