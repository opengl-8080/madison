package madison.trial;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;

import java.util.function.Function;

public class TableViewUtil {

    public static <S, T> void setupCellValueFactory(TableColumn<S, T> column, Function<S, T> getter) {
        column.setCellValueFactory(param -> {
            final T value = getter.apply(param.getValue());
            return new SimpleObjectProperty<>(value);
        });
    }
    
    private TableViewUtil() {}
}
