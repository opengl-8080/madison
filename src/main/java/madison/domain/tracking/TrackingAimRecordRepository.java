package madison.domain.tracking;

import java.util.List;

public interface TrackingAimRecordRepository {
    
    void register(TrackingAimRecord record);

    List<TrackingAimRecord> findAllOrderByDate();
}
