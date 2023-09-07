package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.StatisticsChartData;
import com.guardjo.ticketmanager.web.service.ReservationHistoryService;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatisticsController.class)
class StatisticsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationHistoryService reservationHistoryService;

    @Test
    void testGetStatisticsView() throws Exception {
        StatisticsChartData chartData = StatisticsChartData.from(List.of(TestDataGenerator.reservationHistory(1L)));

        given(reservationHistoryService.findStatisticsChartData()).willReturn(chartData);

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("chartData", chartData))
                .andExpect(view().name("tickets/statistics"));

        then(reservationHistoryService).should().findStatisticsChartData();
    }
}