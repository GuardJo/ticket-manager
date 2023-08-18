package io.github.guardjo.ticketmanager.batch.util;

import io.github.guardjo.ticketmanager.batch.model.HistoryDataType;
import io.github.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Slf4j
public class HistoryDataFileExporter {
    @Value("${history-data.export-path}")
    private String exportPath;

    public void export(ReservationHistoryExportData exportData) {
        log.info("Exporting HistoryData, fileNme = {}", exportData.dataName());

        try {
            File file = new File(exportPath, exportData.dataName() + ".csv");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            String type = (exportData.historyDataType().equals(HistoryDataType.DAILY)) ? "일간" : "주간";

            bufferedWriter.write(type + " 예약 현황," + exportData.dataName());
            bufferedWriter.newLine();
            bufferedWriter.write("신규 예약 수," + exportData.totalNewReservationCount());
            bufferedWriter.newLine();
            bufferedWriter.write("예약 완료 수," + exportData.totalReservationUsedCount());

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            log.error("Failed Export HistoryData, fileName = {}, exception = {}", exportData.dataName(), e.getMessage());
            log.debug("", e);
        }
    }
}
