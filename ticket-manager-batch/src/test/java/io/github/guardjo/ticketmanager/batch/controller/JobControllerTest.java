package io.github.guardjo.ticketmanager.batch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guardjo.ticketmanager.batch.model.JobExecuteRequest;
import io.github.guardjo.ticketmanager.batch.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobController.class)
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JobRegistry jobRegistry;
    @MockBean
    private JobLauncher jobLauncher;

    @DisplayName("API Test (POST) : " + ContextPath.JOB_URL + ContextPath.JOB_EXECUTE_URL)
    @Test
    void testExecuteJob() throws Exception {
        JobExecuteRequest jobExecuteRequest = TestDataGenerator.jobExecuteRequest();
        ExitStatus expectedObject = ExitStatus.COMPLETED;
        Job mockJob = mock(Job.class);
        JobExecution mockExecution = mock(JobExecution.class);

        String requestBody = objectMapper.writeValueAsString(jobExecuteRequest);
        String expected = objectMapper.writeValueAsString(expectedObject);

        given(jobRegistry.getJob(eq(jobExecuteRequest.jobName()))).willReturn(mockJob);
        given(jobLauncher.run(eq(mockJob), eq(jobExecuteRequest.getJobParameters()))).willReturn(mockExecution);
        given(mockExecution.getExitStatus()).willReturn(expectedObject);

        String actual = mockMvc.perform(post(ContextPath.JOB_URL + ContextPath.JOB_EXECUTE_URL)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        assertThat(actual).isEqualTo(expected);

        then(jobRegistry).should().getJob(eq(jobExecuteRequest.jobName()));
        then(jobLauncher).should().run(eq(mockJob), eq(jobExecuteRequest.getJobParameters()));
        then(mockExecution).should().getExitStatus();
    }

    @DisplayName("API Test : (GET) " + ContextPath.JOB_URL + ContextPath.JOB_NAMES_URL)
    @Test
    void testGetJobNames() throws Exception {
        List<String> expectedObject = List.of("jobName1", "jobName2");
        String expected = objectMapper.writeValueAsString(expectedObject);

        given(jobRegistry.getJobNames()).willReturn(expectedObject);

        String actual = mockMvc.perform(get(ContextPath.JOB_URL + ContextPath.JOB_NAMES_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        assertThat(actual).isEqualTo(expected);

        then(jobRegistry).should().getJobNames();
    }
}