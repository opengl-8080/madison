package madison.trial.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CsvFile<T> {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String DEFAULT_DELIMITER = ",";
    private static final List<OpenOption> DEFAULT_OPEN_OPTIONS = List.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    
    private final Path path;
    private final CsvRecordFormatter<T> formatter;
    private final CsvRecordParser<T> parser;
    private final Charset charset;
    private final String delimiter;
    private final List<OpenOption> openOptions;
    
    public static <T> CsvFileBuilder<T> builder(Path path) {
        return new CsvFileBuilder<>(path);
    }

    public CsvFile(
        Path path,
        CsvRecordFormatter<T> formatter,
        CsvRecordParser<T> parser,
        Charset charset,
        String delimiter,
        List<OpenOption> openOptions
    ) {
        this.path = Objects.requireNonNull(path);
        this.formatter = Objects.requireNonNull(formatter);
        this.parser = Objects.requireNonNull(parser);
        this.charset = Objects.requireNonNull(charset);
        this.delimiter = Objects.requireNonNull(delimiter);
        this.openOptions = Objects.requireNonNull(openOptions);
    }
    
    public boolean exists() {
        return Files.isRegularFile(path);
    }
    
    public List<T> load() {
        try {
            List<T> records = new ArrayList<>();
            
            Files.lines(path, charset).forEach(line -> {
                final List<String> elements = Arrays.asList(line.split(delimiter));
                final T record = parser.parse(elements);
                records.add(record);
            });
            
            return records;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public void write(List<T> records) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, openOptions.toArray(OpenOption[]::new))) {
            for (T record : records) {
                final List<String> elements = formatter.format(record);
                writer.write(String.join(delimiter, elements) + "\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public static class CsvFileBuilder<T> {
        private final Path path;
        private CsvRecordFormatter<T> formatter;
        private CsvRecordParser<T> parser;
        private Charset charset = DEFAULT_CHARSET;
        private String delimiter = DEFAULT_DELIMITER;
        private List<OpenOption> openOptions = DEFAULT_OPEN_OPTIONS;

        private CsvFileBuilder(Path path) {
            this.path = Objects.requireNonNull(path);
        }

        public CsvFileBuilder<T> formatter(CsvRecordFormatter<T> formatter) {
            this.formatter = Objects.requireNonNull(formatter);
            return this;
        }

        public CsvFileBuilder<T> parser(CsvRecordParser<T> parser) {
            this.parser = Objects.requireNonNull(parser);
            return this;
        }

        public CsvFileBuilder<T> charset(Charset charset) {
            this.charset = Objects.requireNonNull(charset);
            return this;
        }

        public CsvFileBuilder<T> delimiter(String delimiter) {
            this.delimiter = Objects.requireNonNull(delimiter);
            return this;
        }

        public CsvFileBuilder<T> openOptions(OpenOption... openOptions) {
            this.openOptions = List.of(openOptions);
            return this;
        }
        
        public CsvFile<T> build() {
            return new CsvFile<>(path, formatter, parser, charset, delimiter, openOptions);
        }
    }
}
