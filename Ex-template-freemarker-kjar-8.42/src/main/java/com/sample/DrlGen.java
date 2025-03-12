package com.sample;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DrlGen {

    public static void main(String[] args) throws Exception {

        // Load template
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(DrlGen.class, "/com/sample");
        Template template = cfg.getTemplate("sample_cheese.ftl");

        // Load template input values
        List<String> keyList = Arrays.asList(new String[]{"age", "type", "log"}); // keys in DRT header
        List<Map<String, String>> valueMapList = loadValueMapList("sample_cheese.xls", keyList, 1, 1); // row/col index starts with 0
        Map<String, Object> input = new HashMap<>();
        input.put("valueMapList", valueMapList);

        // Generate a drl
        StringWriter sw = new StringWriter();
        template.process(input, sw);
        String drl = sw.toString();
        System.out.println(drl);

        Path path = Paths.get("src/main/resources/com/sample/cheese.drl");
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.append(drl);
        }
    }

    private static List<Map<String, String>> loadValueMapList(String xlsValueFile, List<String> keyList, int startRow, int startCol) throws EncryptedDocumentException, IOException {
        InputStream is = DrlGen.class.getResourceAsStream(xlsValueFile);
        List<Map<String, String>> valueList = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet1 = workbook.getSheet("sheet1");
        DataFormatter formatter = new DataFormatter(Locale.ENGLISH);
        for (int row = startRow;; row++) {
            Row sheetRow = sheet1.getRow(row);
            if (sheetRow == null) {
                break;
            }
            Map<String, String> valueMap = new HashMap<>();
            for (int col = startCol; col - startCol < keyList.size(); col++) {
                Cell cell = sheetRow.getCell(col);
                valueMap.put(keyList.get(col - startCol), formatter.formatCellValue(cell));
            }
            valueList.add(valueMap);
        }
        //System.out.println(valueList);

        return valueList;
    }
}
