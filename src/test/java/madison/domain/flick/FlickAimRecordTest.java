package madison.domain.flick;

import madison.domain.flick.statistic.FlickAimStatistic;
import madison.domain.flick.statistic.FlickAimTotalScore;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FlickAimRecordTest {

    @Test
    void testCalculateStatistic() {
        final FlickAimRecordDate date = FlickAimRecordDate.of(LocalDate.of(2020, 10, 10));

        final FlickAimRound median = round(6, 10);
        final FlickAimRecord sut = FlickAimRecord.of(date, List.of(
            round(1, 10),
            round(2, 10),
            round(3, 10),
            round(4, 10),
            round(5, 10),
            median,
            round(7, 10),
            round(8, 10),
            round(9, 10),
            round(10, 10),
            round(11, 10)
        ));

        FlickAimStatistic statistic = sut.calculateStatistic();
        
        assertThat(statistic.date()).isEqualTo(date);
        assertThat(statistic.medianRound().value()).isSameAs(median);
        assertThat(statistic.totalScore()).isEqualTo(FlickAimTotalScore.of(66));
    }
    
    private FlickAimRound round(double score, double accuracy) {
        return FlickAimRound.of(FlickAimScore.of(score), FlickAimAccuracy.of(accuracy));
    }
}