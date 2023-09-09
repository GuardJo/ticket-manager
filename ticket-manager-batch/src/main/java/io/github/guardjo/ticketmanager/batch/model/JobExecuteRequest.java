package io.github.guardjo.ticketmanager.batch.model;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.util.Properties;

public record JobExecuteRequest(
        String jobName,
        Properties jobProperties
) {
    public static JobExecuteRequest of(String jobName, Properties jobProperties) {
        return new JobExecuteRequest(jobName, jobProperties);
    }

    public JobParameters getJobParameters() {
        return new JobParametersBuilder(this.jobProperties).toJobParameters();
    }
}
