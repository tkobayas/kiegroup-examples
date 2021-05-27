package com.sample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RuleGen {

    public static void main(String[] args) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("package com.sample\n");
        builder.append("import com.sample.*\n\n");
        builder.append("global java.util.List resultList;\n\n");

        int ruleNum = 2000;

        for (int i = 0; i < ruleNum; i++) {
            builder.append("rule \"rule" + i + "\"\n");
            builder.append("  when\n");
            builder.append("    $p : Person( age >= " + i*5 + " && age < " + (i+1)*5 + " )\n");
            builder.append("  then\n");
            builder.append("    resultList.add( kcontext.getRule().getName() + \" : \" + $p );\n");
            builder.append("end\n");
            builder.append("\n");
        }

        Path path = Paths.get("src/main/resources/com/sample/Sample.drl");
        Files.write(path, builder.toString().getBytes(), StandardOpenOption.CREATE);

        System.out.println("finish");
    }

}
