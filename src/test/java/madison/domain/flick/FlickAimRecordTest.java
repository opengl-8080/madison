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

        final FlickAimRound median = round(date, 6, 10);
        final FlickAimRecord sut = FlickAimRecord.of(List.of(
            round(date, 1, 10),
            round(date, 2, 10),
            round(date, 3, 10),
            round(date, 4, 10),
            round(date, 5, 10),
            median,
            round(date, 7, 10),
            round(date, 8, 10),
            round(date, 9, 10),
            round(date, 10, 10),
            round(date, 11, 10)
        ));

        FlickAimStatistic statistic = sut.calculateStatistic();
        
        assertThat(statistic.date()).isEqualTo(date);
        assertThat(statistic.medianRound().value()).isSameAs(median);
        assertThat(statistic.totalScore()).isEqualTo(FlickAimTotalScore.of(66));
    }
    
    private FlickAimRound round(FlickAimRecordDate date, double score, double accuracy) {
        return FlickAimRound.of(date, FlickAimScore.of(score), FlickAimAccuracy.of(accuracy));
    }
}