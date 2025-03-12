package com.sample;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Generate rules
 */
public class RuleGen {

    public static void main(String[] args) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("package com.sample;\n\n");
        builder.append("import com.sample.Person;\n");

        int ruleNum = 8;

        for (int i = 0; i < ruleNum; i++) {
            builder.append("rule \"rule" + i + "\"\n");
            builder.append("  when\n");
            builder.append("    $p : Person( age == " + i + " )\n");
//            builder.append("    $s : String( this == \"GO" + i + "\" )\n");
            builder.append("    $s : String( this == \"GO\" )\n");
            builder.append("  then\n");
            builder.append("    System.out.println( \"rule" + i + " fired : \" + $p );\n");
            builder.append("end\n");
            builder.append("\n");
        }

        PrintWriter pr = new PrintWriter(new FileWriter(new File("src/main/resources/com/sample/Sample.drl")));
        pr.write(builder.toString());
        pr.close();

        System.out.println("finish");
    }
}
