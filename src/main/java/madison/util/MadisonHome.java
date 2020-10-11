package madison.util;

import gl.util.Directory;
import gl.util.config.ConfigResolver;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MadisonHome {
    private final Directory dir;

    public MadisonHome(ConfigResolver configResolver) {
        final String path = configResolver.resolveRequired("madison.home");
        this.dir = new Directory(Paths.get(path), Directory.InitializeStrategy.CREATE);
    }
    
    public Path flickAimRecordCsv() {
        return dir.path().resolve("flick-aim-record.csv");
    }
    
    public Path trackingAimRecordCsv() {
        return dir.path().resolve("tracking-aim-record.csv");
    }
}
