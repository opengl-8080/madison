package madison.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MadisonHome {
    private static final MadisonHome instance = new MadisonHome();
    
    public static MadisonHome getInstance() {
        return instance;
    }
    
    private final Path home = Paths.get(".");
    
    public Path flickAimRecordCsv() {
        return home.resolve("flick-aim-record.csv");
    }
    
    public Path trackingAimRecordCsv() {
        return home.resolve("tracking-aim-record.csv");
    }
    
    private MadisonHome() {
        System.out.println(home.toAbsolutePath());
    }
}
