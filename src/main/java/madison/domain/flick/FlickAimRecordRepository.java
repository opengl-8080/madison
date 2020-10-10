package madison.domain.flick;

import java.util.List;

public interface FlickAimRecordRepository {
    void register(FlickAimRecord record);
    List<FlickAimRecord> findAllOrderByDate();
}
