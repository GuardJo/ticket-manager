package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import com.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import com.guardjo.ticketmanager.batch.util.TestDataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class WeeklyHistoryDataExportTaskletTest {
    @Mock
    private ReservationHistoryRepository reservationHistoryRepository;
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;

    @InjectMocks
    private WeeklyHistoryDataExportTasklet weeklyHistoryDataExportTasklet;

    @BeforeEach
    void setUp() {
        LocalDate localDate = LocalDate.now();
        LocalDate from = localDate.minusWeeks(1L);
        ReflectionTestUtils.setField(
                weeklyHistoryDataExportTasklet,
                "currentDate",
                localDate.toString(),
                String.class
        );
        List<ReservationHistory> reservationHistories = List.of(TestDataGenerator.reservationHistory());
        given(reservationHistoryRepository.findWeeklyData(eq(from), eq(localDate))).willReturn(reservationHistories);
    }

    @AfterEach
    void tearDown() {
        then(reservationHistoryRepository).should().findWeeklyData(any(LocalDate.class), any(LocalDate.class));
    }

    @DisplayName("weeklyHistoryDataExportTasklet 수행 테스트")
    @Test
    void testExecuteTasklet() throws Exception {
        RepeatStatus actual = weeklyHistoryDataExportTasklet.execute(stepContribution, chunkContext);

        assertThat(actual).isEqualTo(RepeatStatus.FINISHED);
    }
}