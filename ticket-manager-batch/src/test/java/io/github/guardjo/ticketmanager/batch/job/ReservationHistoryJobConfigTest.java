package io.github.guardjo.ticketmanager.batch.job;

import io.github.guardjo.ticketmanager.batch.config.TestBatchConfig;
import io.github.guardjo.ticketmanager.batch.job.tasklet.DailyHistoryDataExportTasklet;
import io.github.guardjo.ticketmanager.batch.job.tasklet.ReservationHistoryBatchTasklet;
import io.github.guardjo.ticketmanager.batch.job.tasklet.WeeklyHistoryDataExportTasklet;
import io.github.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import io.github.guardjo.ticketmanager.batch.util.HistoryDataFileExporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {
        TestBatchConfig.class,
        ReservationHistoryJobConfig.class,
        ReservationHistoryBatchTasklet.class,
        DailyHistoryDataExportTasklet.class,
        WeeklyHistoryDataExportTasklet.class,
        HistoryDataFileExporter.class
})
class ReservationHistoryJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    private final static String TEST_JOB_NAME = "initReservationHistoryJob";
    private final static long TEST_DATA_SIZE = 7L; // data.sql 내 전체 ReservationHistory 개수

    @DisplayName("예약 현황 구성 Job 테스트")
    @Test
    void testInitReservationHistoryJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("from", "2023-05-01")
                .addString("to", "2023-05-03")
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        JobInstance jobInstance = jobExecution.getJobInstance();

        assertThat(jobInstance.getJobName()).isEqualTo(TEST_JOB_NAME);
        assertThat(reservationHistoryRepository.count()).isEqualTo(TEST_DATA_SIZE + 2); // 이틀치만 가져왔기 떄문
    }
}