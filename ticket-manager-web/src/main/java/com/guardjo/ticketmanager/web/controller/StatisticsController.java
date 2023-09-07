package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.StatisticsChartData;
import com.guardjo.ticketmanager.web.service.ReservationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/statistics")
@Controller
@Slf4j
@RequiredArgsConstructor
public class StatisticsController {
    private final ReservationHistoryService reservationHistoryService;

    @GetMapping
    public String getStatisticsView(ModelMap modelMap) {
        log.info("Request Statistics View Page");

        StatisticsChartData chartData = reservationHistoryService.findStatisticsChartData();

        modelMap.addAttribute("chartData", chartData);

        return "tickets/statistics";
    }
}
