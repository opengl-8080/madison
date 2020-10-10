package madison.trial.csv;

import java.util.List;

public interface CsvRecordParser<T> {
    T parse(List<String> elements);
}
