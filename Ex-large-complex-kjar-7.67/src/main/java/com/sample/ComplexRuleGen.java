package com.sample;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Generate complex rules which have AlphaNode + BetaNode
 */
public class ComplexRuleGen {

    public static void main(String[] args) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("package com.sample;\n\n");
        builder.append("import com.sample.FactA;\n");
        builder.append("import com.sample.FactB;\n");
        builder.append("import com.sample.FactC;\n");
        builder.append("import com.sample.FactD;\n");
        builder.append("import com.sample.FactE;\n\n");
        builder.append("global java.util.List resultList;\n\n");

        int ruleNum = 10;

        for (int i = 0; i < ruleNum; i++) {
            builder.append("rule \"rule" + i + "\"\n");
            builder.append("  when\n");
            builder.append("    $a : FactA( value1 == \"" + i + "\" )\n");
            builder.append("    $b : FactB( value1 == \"" + i + "\", value2 == $a.value2, value3 == $a.value3, value4 == $a.value4, value5 == $a.value5 )\n");
            builder.append("    $c : FactC( value1 == \"" + i + "\", value2 == $b.value2, value3 == $b.value3, value4 == $b.value4, value5 == $b.value5 )\n");
            builder.append("    $d : FactD( value1 == \"" + i + "\", value2 == $c.value2, value3 == $c.value3, value4 == $c.value4, value5 == $c.value5 )\n");
            builder.append("    $e : FactE( value1 == \"" + i + "\", value2 == $d.value2, value3 == $d.value3, value4 == $d.value4, value5 == $d.value5 )\n");
            builder.append("  then\n");
            builder.append("    resultList.add( \"rule" + i + " fired : \" + $a );\n");
            builder.append("end\n");
            builder.append("\n");
        }

        PrintWriter pr = new PrintWriter(new FileWriter(new File("src/main/resources/com/sample/Sample.drl")));
        pr.write(builder.toString());
        pr.close();

        System.out.println("finish");
    }
}
