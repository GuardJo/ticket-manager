package io.github.guardjo.ticketmanager.batch.controller;

import io.github.guardjo.ticketmanager.batch.model.JobExecuteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "배치 수행 API", description = "배치 수행 관련 API 목록")
@RestController
@RequestMapping(ContextPath.JOB_URL)
@Slf4j
@RequiredArgsConstructor
public class JobController {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Operation(summary = "특정 배치 수행 요청", description = "주어진 jobName과 jobParameter를 기반으로 해당하는 배치 JOB 수행")
    @PostMapping(ContextPath.JOB_EXECUTE_URL)
    public ExitStatus executeJob(@RequestBody JobExecuteRequest jobExecuteRequest) throws NoSuchJobException,
            JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if (!StringUtils.hasLength(jobExecuteRequest.jobName())) {
            log.warn("Invalid JobExecuteRequest");
            return ExitStatus.FAILED;
        }

        Job executeJob = jobRegistry.getJob(jobExecuteRequest.jobName());

        log.info("Request Job Execute, jobName = {}", jobExecuteRequest.jobName());

        return jobLauncher.run(executeJob, jobExecuteRequest.getJobParameters()).getExitStatus();
    }

    @Operation(summary = "배치 JOB 이름 목록 반환", description = "현재 수행 가능한 배치 JOB 이름에 대한 전체 목록을 반환")
    @GetMapping(ContextPath.JOB_NAMES_URL)
    public List<String> getJobNames() {
        log.info("Request Job Names");

        return jobRegistry.getJobNames().stream().toList();
    }
}
