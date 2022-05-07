package com.korotkov.main.service.file;

import com.korotkov.main.config.PortalConstants;
import com.korotkov.main.service.scoringModel.additionalService.additionalEntityForPortal.EntityForExportFile.ScoringModelExportFile;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelExportScoringModelData extends AbstractXlsxView implements PortalConstants {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Sheet sheetCommonInfo = workbook.createSheet("Common Information");
        response.setHeader("Content-Disposition", "attachment; filename=ScoringModel.xlsx");
        sheetCommonInfo.setColumnWidth(0, 23 * 256);
        sheetCommonInfo.setColumnWidth(1, 31 * 256);
        sheetCommonInfo.setColumnWidth(2, 21 * 256);

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

        ScoringModelExportFile scoringModelExportFile = (ScoringModelExportFile) model.get("modelObject");

        Row titleRow = sheetCommonInfo.createRow(0);
        titleRow.createCell(0).setCellValue("Scoring Model: " + scoringModelExportFile.getTitleModel());
        titleRow.getCell(0).setCellStyle(styleTitle);

        Row titleCreatedRow = sheetCommonInfo.createRow(1);
        titleCreatedRow.createCell(0).setCellValue("Model was created at:");
        titleCreatedRow.getCell(0).setCellStyle(styleHeaders);
        titleCreatedRow.createCell(1).setCellValue(scoringModelExportFile.getCreatedAt());
        titleCreatedRow.getCell(1).setCellStyle(styleCreatedDateModel);
        Row titleRowUsedService = sheetCommonInfo.createRow(2);
        titleRowUsedService.createCell(0).setCellValue("Used service:");
        titleRowUsedService.createCell(1).setCellValue(MAIN_DOMAIN_URL);

        if (scoringModelExportFile.getAllParametersInfluenceOneTotal().size() > 0) {
            Row rowInfluenceText = sheetCommonInfo.createRow(5);
            rowInfluenceText.createCell(0).setCellValue("Weight of Influence of all attributes in data which were sent for building model:");
            rowInfluenceText.getCell(0).setCellStyle(styleTitle);

            Row rowHeadersAllOneAttributes = sheetCommonInfo.createRow(7);
            rowHeadersAllOneAttributes.createCell(0).setCellValue("#");
            rowHeadersAllOneAttributes.getCell(0).setCellStyle(styleHeaders);
            rowHeadersAllOneAttributes.createCell(1).setCellValue("Attribute name");
            rowHeadersAllOneAttributes.getCell(1).setCellStyle(styleHeaders);
            rowHeadersAllOneAttributes.createCell(2).setCellValue("Information Value, %");
            rowHeadersAllOneAttributes.getCell(2).setCellStyle(styleHeaders);

            int rowsCountSheetCommonInfo = 8;

            for(int a = 0; a < scoringModelExportFile.getAllParametersInfluenceOneTotal().size(); a++){
                Row rowNameAttribute = sheetCommonInfo.createRow(rowsCountSheetCommonInfo);
                rowNameAttribute.createCell(0).setCellValue(a + 1);
                rowNameAttribute.createCell(1).setCellValue(scoringModelExportFile.getAllParametersInfluenceOneTotal()
                        .get(a).getNameParameter());
                rowNameAttribute.createCell(2).setCellValue(scoringModelExportFile.getAllParametersInfluenceOneTotal()
                        .get(a).getIv());
                rowNameAttribute.getCell(2).setCellStyle(styleCellPercent);
                rowsCountSheetCommonInfo++;
            }

            if (scoringModelExportFile.getScoringModelRecommendedParametersListOne().size() > 0 ||
            scoringModelExportFile.getScoringModelRecommendedParametersListTwo().size() > 0) {
                Sheet sheetScoringModel = workbook.createSheet("Scoring Model");
                sheetScoringModel.setColumnWidth(0, 17 * 256);
                sheetScoringModel.setColumnWidth(1, 18 * 256);
                sheetScoringModel.setColumnWidth(2, 15 * 256);
                sheetScoringModel.setColumnWidth(3, 14 * 256);
                sheetScoringModel.setColumnWidth(4, 17 * 256);
                sheetScoringModel.setColumnWidth(5, 16 * 256);
                sheetScoringModel.setColumnWidth(6, 15 * 256);
                sheetScoringModel.setColumnWidth(7, 16 * 256);
                sheetScoringModel.setColumnWidth(8, 16 * 256);
                sheetScoringModel.setColumnWidth(9, 16 * 256);
                sheetScoringModel.setColumnWidth(10, 16 * 256);
                sheetScoringModel.setColumnWidth(11, 16 * 256);
                sheetScoringModel.setColumnWidth(12, 16 * 256);
                sheetScoringModel.setColumnWidth(13, 13 * 256);
                sheetScoringModel.setColumnWidth(14, 13 * 256);

                Row rowTitleScoringModel = sheetScoringModel.createRow(0);
                rowTitleScoringModel.createCell(0).setCellValue("Below is the scoring model with extended information for each attribute");
                rowTitleScoringModel.getCell(0).setCellStyle(styleTitle);

                int rowsCountSheetScoringModel = 3;

                if (scoringModelExportFile.getScoringModelRecommendedParametersListOne().size() > 0) {
                    Row rowTitleOneAttribute = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                    rowTitleOneAttribute.createCell(0).setCellValue("Recommended Attributes without concat with others:");
                    rowTitleOneAttribute.getCell(0).setCellStyle(styleTitle);

                    rowsCountSheetScoringModel += 2;

                    for(int a = 0; a < scoringModelExportFile.getScoringModelRecommendedParametersListOne().size(); a++){
                        Row rowNameParameter = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowNameParameter.createCell(0).setCellValue("Attribute name: " + scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getNameParameter());
                        rowNameParameter.getCell(0).setCellStyle(styleTitle);

                        rowsCountSheetScoringModel++;

                        Row rowHeadersAttributeValue = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowHeadersAttributeValue.createCell(0).setCellValue("Attribute value");
                        rowHeadersAttributeValue.getCell(0).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(1).setCellValue("Count of Scores");
                        rowHeadersAttributeValue.getCell(1).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(2).setCellValue("Count of Good");
                        rowHeadersAttributeValue.getCell(2).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(3).setCellValue("Count of Bad");
                        rowHeadersAttributeValue.getCell(3).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(4).setCellValue("Rate of Good, %");
                        rowHeadersAttributeValue.getCell(4).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(5).setCellValue("Rate of Bad, %");
                        rowHeadersAttributeValue.getCell(5).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(6).setCellValue("Count of Total");
                        rowHeadersAttributeValue.getCell(6).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(7).setCellValue("Population Rate of Good, %");
                        rowHeadersAttributeValue.getCell(7).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(8).setCellValue("Population Rate of Bad, %");
                        rowHeadersAttributeValue.getCell(8).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(9).setCellValue("Population Rate of Total, %");
                        rowHeadersAttributeValue.getCell(9).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(10).setCellValue("Rate of Good from total good in Attribute");
                        rowHeadersAttributeValue.getCell(10).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(11).setCellValue("Rate of Bad from total bad in Attribute");
                        rowHeadersAttributeValue.getCell(11).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(12).setCellValue("Measure of Probability of good");
                        rowHeadersAttributeValue.getCell(12).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(13).setCellValue("Weight of Evidence");
                        rowHeadersAttributeValue.getCell(13).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(14).setCellValue("Information Value");
                        rowHeadersAttributeValue.getCell(14).setCellStyle(styleHeaders);

                        rowsCountSheetScoringModel++;

                        for(int b = 0; b < scoringModelExportFile.getScoringModelRecommendedParametersListOne().get(a)
                                .getScoringParameterPortalRows().size(); b++){
                            Row rowAttributeValue = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                            rowAttributeValue.createCell(0).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getTitle());
                            rowAttributeValue.createCell(1).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getScore());
                            rowAttributeValue.createCell(2).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodCount());
                            rowAttributeValue.createCell(3).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadCount());
                            rowAttributeValue.createCell(4).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodRate() / 100);
                            rowAttributeValue.getCell(4).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(5).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadRate() / 100);
                            rowAttributeValue.getCell(5).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(6).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getTotalCount());
                            rowAttributeValue.createCell(7).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodPopulationPercent() / 100);
                            rowAttributeValue.getCell(7).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(8).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadPopulationPercent() / 100);
                            rowAttributeValue.getCell(8).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(9).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getTotalPopulationPercent() / 100);
                            rowAttributeValue.getCell(9).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(10).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getGiG());
                            rowAttributeValue.createCell(11).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getBiB());
                            rowAttributeValue.createCell(12).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getPgPb());
                            rowAttributeValue.createCell(13).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getWoe());
                            rowAttributeValue.createCell(14).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListOne().get(a).getScoringParameterPortalRows().get(b)
                                    .getIv());

                            rowsCountSheetScoringModel++;
                        }
                        Row rowTotalAttribute = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowTotalAttribute.createCell(0).setCellValue("Total");
                        rowTotalAttribute.getCell(0).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(2).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getGoodCountTotal());
                        rowTotalAttribute.getCell(2).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(3).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getBadCountTotal());
                        rowTotalAttribute.getCell(3).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(4).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getGoodRateTotal() / 100);
                        rowTotalAttribute.getCell(4).setCellStyle(styleCellPercentAndHeader);
                        rowTotalAttribute.createCell(5).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getBadRateTotal() / 100);
                        rowTotalAttribute.getCell(5).setCellStyle(styleCellPercentAndHeader);
                        rowTotalAttribute.createCell(6).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getTotalCountTotal());
                        rowTotalAttribute.getCell(6).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(14).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListOne().get(a).getIvTotal());
                        rowTotalAttribute.getCell(14).setCellStyle(styleHeaders);

                        rowsCountSheetScoringModel += 2;
                    }

                    rowsCountSheetScoringModel += 1;
                }

                if (scoringModelExportFile.getScoringModelRecommendedParametersListTwo().size() > 0) {
                    Row rowTitleTwoAttribute = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                    rowTitleTwoAttribute.createCell(0).setCellValue("Recommended Attributes with concated two attributes:");
                    rowTitleTwoAttribute.getCell(0).setCellStyle(styleTitle);

                    rowsCountSheetScoringModel += 2;

                    for(int a = 0; a < scoringModelExportFile.getScoringModelRecommendedParametersListTwo().size(); a++){
                        Row rowNameParameter = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowNameParameter.createCell(0).setCellValue("Attribute name: " + scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getNameParameter());
                        rowNameParameter.getCell(0).setCellStyle(styleTitle);

                        rowsCountSheetScoringModel++;

                        Row rowHeadersAttributeValue = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowHeadersAttributeValue.createCell(0).setCellValue("Attribute value");
                        rowHeadersAttributeValue.getCell(0).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(1).setCellValue("Count of Scores");
                        rowHeadersAttributeValue.getCell(1).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(2).setCellValue("Count of Good");
                        rowHeadersAttributeValue.getCell(2).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(3).setCellValue("Count of Bad");
                        rowHeadersAttributeValue.getCell(3).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(4).setCellValue("Rate of Good, %");
                        rowHeadersAttributeValue.getCell(4).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(5).setCellValue("Rate of Bad, %");
                        rowHeadersAttributeValue.getCell(5).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(6).setCellValue("Count of Total");
                        rowHeadersAttributeValue.getCell(6).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(7).setCellValue("Population Rate of Good, %");
                        rowHeadersAttributeValue.getCell(7).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(8).setCellValue("Population Rate of Bad, %");
                        rowHeadersAttributeValue.getCell(8).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(9).setCellValue("Population Rate of Total, %");
                        rowHeadersAttributeValue.getCell(9).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(10).setCellValue("Rate of Good from total good in Attribute");
                        rowHeadersAttributeValue.getCell(10).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(11).setCellValue("Rate of Bad from total bad in Attribute");
                        rowHeadersAttributeValue.getCell(11).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(12).setCellValue("Measure of Probability of good");
                        rowHeadersAttributeValue.getCell(12).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(13).setCellValue("Weight of Evidence");
                        rowHeadersAttributeValue.getCell(13).setCellStyle(styleHeaders);
                        rowHeadersAttributeValue.createCell(14).setCellValue("Information Value");
                        rowHeadersAttributeValue.getCell(14).setCellStyle(styleHeaders);

                        rowsCountSheetScoringModel++;

                        for(int b = 0; b < scoringModelExportFile.getScoringModelRecommendedParametersListTwo().get(a)
                                .getScoringParameterPortalRows().size(); b++){
                            Row rowAttributeValue = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                            rowAttributeValue.createCell(0).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getTitle());
                            rowAttributeValue.createCell(1).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getScore());
                            rowAttributeValue.createCell(2).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodCount());
                            rowAttributeValue.createCell(3).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadCount());
                            rowAttributeValue.createCell(4).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodRate() / 100);
                            rowAttributeValue.getCell(4).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(5).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadRate() / 100);
                            rowAttributeValue.getCell(5).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(6).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getTotalCount());
                            rowAttributeValue.createCell(7).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getGoodPopulationPercent() / 100);
                            rowAttributeValue.getCell(7).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(8).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getBadPopulationPercent() / 100);
                            rowAttributeValue.getCell(8).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(9).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getTotalPopulationPercent() / 100);
                            rowAttributeValue.getCell(9).setCellStyle(styleCellPercent);
                            rowAttributeValue.createCell(10).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getGiG());
                            rowAttributeValue.createCell(11).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getBiB());
                            rowAttributeValue.createCell(12).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getPgPb());
                            rowAttributeValue.createCell(13).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getWoe());
                            rowAttributeValue.createCell(14).setCellValue(scoringModelExportFile
                                    .getScoringModelRecommendedParametersListTwo().get(a).getScoringParameterPortalRows().get(b)
                                    .getIv());

                            rowsCountSheetScoringModel++;
                        }
                        Row rowTotalAttribute = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                        rowTotalAttribute.createCell(0).setCellValue("Total");
                        rowTotalAttribute.getCell(0).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(2).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getGoodCountTotal());
                        rowTotalAttribute.getCell(2).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(3).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getBadCountTotal());
                        rowTotalAttribute.getCell(3).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(4).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getGoodRateTotal() / 100);
                        rowTotalAttribute.getCell(4).setCellStyle(styleCellPercentAndHeader);
                        rowTotalAttribute.createCell(5).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getBadRateTotal() / 100);
                        rowTotalAttribute.getCell(5).setCellStyle(styleCellPercentAndHeader);
                        rowTotalAttribute.createCell(6).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getTotalCountTotal());
                        rowTotalAttribute.getCell(6).setCellStyle(styleHeaders);
                        rowTotalAttribute.createCell(14).setCellValue(scoringModelExportFile
                                .getScoringModelRecommendedParametersListTwo().get(a).getIvTotal());
                        rowTotalAttribute.getCell(14).setCellStyle(styleHeaders);

                        rowsCountSheetScoringModel += 2;
                    }

                    rowsCountSheetScoringModel += 1;
                }

                Row rowFinishThanks = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                rowFinishThanks.createCell(0).setCellValue("Thank you for using \"Scoring Machine\"!");
                rowFinishThanks.getCell(0).setCellStyle(styleTitle);

                rowsCountSheetScoringModel +=1;

                Row rowFinishLink = sheetScoringModel.createRow(rowsCountSheetScoringModel);
                rowFinishLink.createCell(0).setCellValue(MAIN_DOMAIN_URL);
                rowFinishLink.getCell(0).setCellStyle(styleTitle);
            }
        }
    }
}
