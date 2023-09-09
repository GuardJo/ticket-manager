package io.github.guardjo.ticketmanager.batch.job.tasklet;

import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import io.github.guardjo.ticketmanager.common.repository.ReservationHistoryRepository;
import io.github.guardjo.ticketmanager.batch.util.HistoryDataFileExporter;
import io.github.guardjo.ticketmanager.batch.util.TestDataGenerator;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class DailyHistoryDataExportTaskletTest {
    @Mock
    private ReservationHistoryRepository reservationHistoryRepository;
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private HistoryDataFileExporter historyDataFileExporter;

    @InjectMocks
    private DailyHistoryDataExportTasklet dailyHistoryDataExportTasklet;

    @BeforeEach
    void setUp() {
        String date = "2023-04-30";
        ReflectionTestUtils.setField(
                dailyHistoryDataExportTasklet,
                "currentDate",
                date,
                String.class
        );

        ReservationHistory reservationHistory = TestDataGenerator.reservationHistory();

        given(reservationHistoryRepository.findByHistoryDate(eq(LocalDate.parse(date))))
                .willReturn(Optional.of(reservationHistory));
        willDoNothing().given(historyDataFileExporter).export(any(ReservationHistoryExportData.class));
    }

    @AfterEach
    void tearDown() {
        then(reservationHistoryRepository).should().findByHistoryDate(any(LocalDate.class));
        then(historyDataFileExporter).should().export(any(ReservationHistoryExportData.class));
    }

    @DisplayName("dailyHistoryDataExportTasklet 수행 테스트")
    @Test
    void testExecuteTasklet() throws Exception {
        RepeatStatus actual = dailyHistoryDataExportTasklet.execute(stepContribution, chunkContext);

        assertThat(actual).isEqualTo(RepeatStatus.FINISHED);
    }
}