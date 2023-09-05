package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.StatisticsChartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/statistics")
@Controller
@Slf4j
public class StatisticsController {
    @GetMapping
    public String getStatisticsView(ModelMap modelMap) {
        log.info("Request Statistics View Page");

        StatisticsChartData chartData = StatisticsChartData.of(
                List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                List.of(1L, 3L, 4L, 2L, 5L, 3L, 0L, 1L, 9L, 10L),
                List.of(10L, 0L, 1L, 3L, 4L, 2L, 6L, 2L, 3L, 10L)
        );

        modelMap.addAttribute("chartData", chartData);

        return "tickets/statistics";
    }
}
