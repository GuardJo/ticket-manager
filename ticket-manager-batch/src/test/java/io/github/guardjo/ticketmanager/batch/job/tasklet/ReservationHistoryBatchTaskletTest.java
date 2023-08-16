package io.github.guardjo.ticketmanager.batch.job.tasklet;

import io.github.guardjo.ticketmanager.batch.domain.Reservation;
import io.github.guardjo.ticketmanager.batch.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import io.github.guardjo.ticketmanager.batch.repository.ReservationRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ReservationHistoryBatchTaskletTest {
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationHistoryRepository reservationHistoryRepository;

    @InjectMocks
    private ReservationHistoryBatchTasklet reservationHistoryBatchTasklet;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
                reservationHistoryBatchTasklet,
                "startDateStr", "2023-05-01", String.class
        );
        ReflectionTestUtils.setField(
                reservationHistoryBatchTasklet,
                "finishDateStr", "2023-05-02", String.class
        );
        Reservation testDate = TestDataGenerator.reservation();

        given(reservationHistoryRepository.existsByHistoryDate(any(LocalDate.class))).willReturn(false);
        given(reservationRepository.findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of(testDate));
        given(reservationRepository.findAllByTodayNewReservations(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(List.of());
        given(reservationHistoryRepository.save(any(ReservationHistory.class))).willReturn(mock(ReservationHistory.class));
    }

    @AfterEach
    void tearDown() {
        then(reservationHistoryRepository).should().existsByHistoryDate(any(LocalDate.class));
        then(reservationRepository).should().findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(
                any(LocalDateTime.class), any(LocalDateTime.class));
        then(reservationRepository).should().findAllByTodayNewReservations(
                any(LocalDateTime.class), any(LocalDateTime.class));
        then(reservationHistoryRepository).should().save(any(ReservationHistory.class));
    }

    @DisplayName("예약 현황 저장 tasket 테스트")
    @Test
    void testExecute() throws Exception {
        RepeatStatus actual = reservationHistoryBatchTasklet.execute(stepContribution, chunkContext);

        assertThat(actual).isEqualTo(RepeatStatus.FINISHED);
    }
}