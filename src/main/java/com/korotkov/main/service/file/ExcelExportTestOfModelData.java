package com.korotkov.main.service.file;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile.TestOfModelExportFile;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelExportTestOfModelData extends AbstractXlsxView implements PortalConstants {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Sheet sheet = workbook.createSheet("Test of Scoring Model");
        response.setHeader("Content-Disposition", "attachment; filename=TestOfScoringModel.xlsx");
        sheet.setColumnWidth(0, 4 * 256);
        sheet.setColumnWidth(1, 22 * 256);
        sheet.setColumnWidth(2, 22 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 18 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 18 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 18 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 15 * 256);

        Font fontHeaders = workbook.createFont();
        fontHeaders.setFontName("Arial");
        fontHeaders.setFontHeightInPoints((short) 12);
        fontHeaders.setBold(true);

        CellStyle styleTitle = workbook.createCellStyle();
        styleTitle.setFont(fontHeaders);

        CellStyle styleCreatedDateModel = workbook.createCellStyle();
        styleCreatedDateModel.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        styleCreatedDateModel.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleCreatedDateModel.setFont(fontHeaders);
        CreationHelper createHelper = workbook.getCreationHelper();
        styleCreatedDateModel.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy HH:mm:ss"));
        styleCreatedDateModel.setWrapText(true);

        CellStyle styleHeaders = workbook.createCellStyle();
        styleHeaders.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        styleHeaders.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeaders.setFont(fontHeaders);
        styleHeaders.setWrapText(true);

        CellStyle styleCellPercent = workbook.createCellStyle();
        styleCellPercent.setDataFormat(createHelper.createDataFormat().getFormat("##.##%"));
        styleCellPercent.setWrapText(true);

        CellStyle styleCellPercentAndHeader = workbook.createCellStyle();
        styleCellPercentAndHeader.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        styleCellPercentAndHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleCellPercentAndHeader.setFont(fontHeaders);
        styleCellPercentAndHeader.setDataFormat(createHelper.createDataFormat().getFormat("##.##%"));
        styleCellPercentAndHeader.setWrapText(true);

        CellStyle styleSimpleText = workbook.createCellStyle();
        styleSimpleText.setDataFormat(createHelper.createDataFormat().getFormat("text"));

        TestOfModelExportFile testOfModelExportFile = (TestOfModelExportFile) model.get("modelObject");

        Row titleTestRow = sheet.createRow(0);
        titleTestRow.createCell(0).setCellValue("Test name: " + testOfModelExportFile.getTitleTest());
        titleTestRow.getCell(0).setCellStyle(styleTitle);
        Row titleScoringModelRow = sheet.createRow(1);
        titleScoringModelRow.createCell(0).setCellValue("For scoring model: " + testOfModelExportFile.getTitleScoringModel());
        Row titleGiniRow = sheet.createRow(2);
        titleGiniRow.createCell(1).setCellValue("Gini:");
        titleGiniRow.getCell(1).setCellStyle(styleHeaders);
        titleGiniRow.createCell(2).setCellValue(testOfModelExportFile.getGiniIndex());
        titleGiniRow.getCell(2).setCellStyle(styleCellPercentAndHeader);
        Row titleCreatedRow = sheet.createRow(3);
        titleCreatedRow.createCell(1).setCellValue("Test was created at:");
        titleCreatedRow.getCell(1).setCellStyle(styleHeaders);
        titleCreatedRow.createCell(2).setCellValue(testOfModelExportFile.getCreatedAtTest());
        titleCreatedRow.getCell(2).setCellStyle(styleCreatedDateModel);
        Row titleUsedService = sheet.createRow(4);
        titleUsedService.createCell(1).setCellValue("Used service:");
        titleUsedService.createCell(2).setCellValue(MAIN_DOMAIN_URL);

        int rowCount = 6;

        if (testOfModelExportFile.getTestScoringModelResultTotal() != null &&
                !testOfModelExportFile.getListTestResultsRowsWithoutTotal().isEmpty()){
            Row headersTableRow = sheet.createRow(rowCount);
            headersTableRow.createCell(0).setCellValue("#");
            headersTableRow.getCell(0).setCellStyle(styleHeaders);
            headersTableRow.createCell(1).setCellValue("Count of scores");
            headersTableRow.getCell(1).setCellStyle(styleHeaders);
            headersTableRow.createCell(2).setCellValue("Total items");
            headersTableRow.getCell(2).setCellStyle(styleHeaders);
            headersTableRow.createCell(3).setCellValue("Count of Good");
            headersTableRow.getCell(3).setCellStyle(styleHeaders);
            headersTableRow.createCell(4).setCellValue("Count of Bad");
            headersTableRow.getCell(4).setCellStyle(styleHeaders);
            headersTableRow.createCell(5).setCellValue("Bad Rate, %");
            headersTableRow.getCell(5).setCellStyle(styleHeaders);
            headersTableRow.createCell(6).setCellValue("Cum. Total count");
            headersTableRow.getCell(6).setCellStyle(styleHeaders);
            headersTableRow.createCell(7).setCellValue("Cum. Total, %");
            headersTableRow.getCell(7).setCellStyle(styleHeaders);
            headersTableRow.createCell(8).setCellValue("Cum. Good count");
            headersTableRow.getCell(8).setCellStyle(styleHeaders);
            headersTableRow.createCell(9).setCellValue("Cum. Good, %");
            headersTableRow.getCell(9).setCellStyle(styleHeaders);
            headersTableRow.createCell(10).setCellValue("Cum. Bad count");
            headersTableRow.getCell(10).setCellStyle(styleHeaders);
            headersTableRow.createCell(11).setCellValue("Cum. Bad, %");
            headersTableRow.getCell(11).setCellStyle(styleHeaders);
            headersTableRow.createCell(12).setCellValue("Gini Index");
            headersTableRow.getCell(12).setCellStyle(styleHeaders);

            rowCount += 1;

            for (int a = 0; a < testOfModelExportFile.getListTestResultsRowsWithoutTotal().size(); a++){
                Row rowTable = sheet.createRow(rowCount);
                rowTable.createCell(0).setCellValue(a + 1);
                rowTable.createCell(1).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getScore());
                rowTable.getCell(1).setCellStyle(styleSimpleText);
                rowTable.createCell(2).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCountTotalItems());
                rowTable.createCell(3).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCountGoodItems());
                rowTable.createCell(4).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCountBadItems());
                rowTable.createCell(5).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getBadRate());
                rowTable.getCell(5).setCellStyle(styleCellPercent);
                rowTable.createCell(6).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumTotalItemsCount());
                rowTable.createCell(7).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumTotalItemsPercent());
                rowTable.getCell(7).setCellStyle(styleCellPercent);
                rowTable.createCell(8).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumGoodItemsCount());
                rowTable.createCell(9).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumGoodItemsPercent());
                rowTable.getCell(9).setCellStyle(styleCellPercent);
                rowTable.createCell(10).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumBadItemsCount());
                rowTable.createCell(11).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getCumBadItemsPercent());
                rowTable.getCell(11).setCellStyle(styleCellPercent);
                rowTable.createCell(12).setCellValue(testOfModelExportFile.getListTestResultsRowsWithoutTotal()
                        .get(a).getGiniResult());
                rowTable.getCell(12).setCellStyle(styleCellPercent);
                rowCount++;
            }
            Row rowTotalTable = sheet.createRow(rowCount);
            rowTotalTable.createCell(1).setCellValue("Total");
            rowTotalTable.getCell(1).setCellStyle(styleHeaders);
            rowTotalTable.createCell(2).setCellValue(testOfModelExportFile.getTestScoringModelResultTotal()
                    .getCountTotalItems());
            rowTotalTable.getCell(2).setCellStyle(styleHeaders);
            rowTotalTable.createCell(3).setCellValue(testOfModelExportFile.getTestScoringModelResultTotal()
                    .getCountGoodItems());
            rowTotalTable.getCell(3).setCellStyle(styleHeaders);
            rowTotalTable.createCell(4).setCellValue(testOfModelExportFile.getTestScoringModelResultTotal()
                    .getCountBadItems());
            rowTotalTable.getCell(4).setCellStyle(styleHeaders);
            rowTotalTable.createCell(5).setCellValue(testOfModelExportFile.getTestScoringModelResultTotal()
                    .getBadRate());
            rowTotalTable.getCell(5).setCellStyle(styleCellPercentAndHeader);
            rowTotalTable.createCell(12).setCellValue(testOfModelExportFile.getTestScoringModelResultTotal()
                    .getGiniResult());
            rowTotalTable.getCell(12).setCellStyle(styleCellPercentAndHeader);

            rowCount += 2;
        }

        Row rowFinishThanks = sheet.createRow(rowCount);
        rowFinishThanks.createCell(0).setCellValue("Thank you for using \"Scoring Machine\"!");
        rowFinishThanks.getCell(0).setCellStyle(styleTitle);

        rowCount += 1;

        Row rowFinishLink = sheet.createRow(rowCount);
        rowFinishLink.createCell(0).setCellValue(MAIN_DOMAIN_URL);
        rowFinishLink.getCell(0).setCellStyle(styleTitle);
    }
}
