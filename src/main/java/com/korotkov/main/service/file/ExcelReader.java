package com.korotkov.main.service.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class ExcelReader {
    private static final DataFormatter formatter = new DataFormatter();

    public static ArrayList<ArrayList<String>> readFileExcel(MultipartFile file) throws IOException {
        Workbook workbook = loadWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        return processSheet(sheet);
    }

    private static ArrayList<ArrayList<String>> processSheet(Sheet sheet){
        ArrayList<ArrayList<String>> finalList = new ArrayList<>();
        Iterator<Row> iterator = sheet.rowIterator();
        for(int rowIndex = 0; iterator.hasNext(); rowIndex++){
            Row row = iterator.next();
            processRow(finalList,rowIndex,row);
        }
        return finalList;
    }

    private static void processRow(ArrayList<ArrayList<String>> map, int rowIndex, Row row){
        map.add(rowIndex, new ArrayList<String>());
        for (Cell cell : row){
            processCell(cell, map.get(rowIndex));
        }
    }

    private static void processCell(Cell cell, ArrayList<String> dataRow){
        dataRow.add(formatter.formatCellValue(cell));
    }

    private static Workbook loadWorkbook(MultipartFile file) throws IOException {
        String extension =
                Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        FileInputStream fileInputStream = new FileInputStream(convertMultipartToFile(file));
        switch (extension){
            case "xls":
                return new HSSFWorkbook(fileInputStream);
            case "xlsx":
                return new XSSFWorkbook(fileInputStream);
            default:
                throw new RuntimeException("Unknown Excel file extension: " + extension);
        }
    }

    private static File convertMultipartToFile(MultipartFile file) throws IOException{
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convFile;
    }
}
