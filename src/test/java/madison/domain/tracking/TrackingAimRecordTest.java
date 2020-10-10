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
        final TrackingAimRound median = round(6, 10, 10);
        
        final TrackingAimRecord sut = TrackingAimRecord.of(date, List.of(
            round(1, 10, 10),
            round(2, 10, 10),
            round(3, 10, 10),
            round(4, 10, 10),
            round(5, 10, 10),
            median,
            round(7, 10, 10),
            round(8, 10, 10),
            round(9, 10, 10),
            round(10, 10, 10),
            round(11, 10, 10)
        ));
        
        TrackingAimStatistic statistic = sut.calculateStatistic();
        
        assertThat(statistic.date()).isEqualTo(date);
        assertThat(statistic.medianRound().value()).isSameAs(median);
        assertThat(statistic.totalScore()).isEqualTo(TrackingAimTotalScore.of(66));
    }
    
    private TrackingAimRound round(double score, double accuracy, double damageEff) {
        return TrackingAimRound.of(TrackingAimScore.of(score), TrackingAimAccuracy.of(accuracy), TrackingAimDamageEff.of(damageEff));
    }
}