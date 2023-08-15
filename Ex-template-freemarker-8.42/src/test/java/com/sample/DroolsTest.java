package com.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testTemplate() throws Exception {

        // Load template
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(DroolsTest.class, "/com/sample");
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
        //System.out.println(drl);

        // build the generated drl
        KieServices ks = KieServices.get();
        final Resource drlResource = ks.getResources().newReaderResource(new StringReader(drl));
        drlResource.setTargetPath("src/main/resources/rule.drl");

        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write("src/main/resources/com/sample/rule.drl", drlResource);
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);
        ks.newKieBuilder(kfs).buildAll();
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        KieBase kbase = kcontainer.getKieBase();

        final Collection<KiePackage> pkgs = kbase.getKiePackages();

        System.out.println("pkgs = " + pkgs);

        final KieSession ksession = kbase.newKieSession();

        ksession.insert(new Cheese("stilton"));
        ksession.insert(new Person("michael", 42));
        final List<String> list = new ArrayList<String>();
        ksession.setGlobal("list", list);

        ksession.fireAllRules();

        System.out.println("list = " + list.toString());

        ksession.dispose();

    }

    private List<Map<String, String>> loadValueMapList(String xlsValueFile, List<String> keyList, int startRow, int startCol) throws EncryptedDocumentException, IOException {
        InputStream is = this.getClass().getResourceAsStream(xlsValueFile);
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
