package madison.domain.tracking;

import madison.domain.tracking.statistic.TrackingAimStatistic;
import madison.domain.tracking.statistic.TrackingAimTotalScore;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TrackingAimRecordTest {

    @Test
    void testCalculateStatistic() {
        final TrackingAimRecordDate date = TrackingAimRecordDate.of(LocalDate.of(2020, 11, 12));
        final TrackingAimRound median = round(date, 6, 10, 10);
        
        final TrackingAimRecord sut = TrackingAimRecord.of(List.of(
            round(date, 1, 10, 10),
            round(date, 2, 10, 10),
            round(date, 3, 10, 10),
            round(date, 4, 10, 10),
            round(date, 5, 10, 10),
            median,
            round(date, 7, 10, 10),
            round(date, 8, 10, 10),
            round(date, 9, 10, 10),
            round(date, 10, 10, 10),
            round(date, 11, 10, 10)
        ));
        
        TrackingAimStatistic statistic = sut.calculateStatistic();
        
        assertThat(statistic.date()).isEqualTo(date);
        assertThat(statistic.medianRound().value()).isSameAs(median);
        assertThat(statistic.totalScore()).isEqualTo(TrackingAimTotalScore.of(66));
    }
    
    private TrackingAimRound round(TrackingAimRecordDate date, double score, double accuracy, double damageEff) {
        return TrackingAimRound.of(date, TrackingAimScore.of(score), TrackingAimAccuracy.of(accuracy), TrackingAimDamageEff.of(damageEff));
    }
}