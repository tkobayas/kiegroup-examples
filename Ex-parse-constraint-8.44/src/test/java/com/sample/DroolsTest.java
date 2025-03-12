package com.sample;

import java.util.List;

import org.drools.drl.ast.descr.BaseDescr;
import org.drools.drl.ast.descr.ConstraintConnectiveDescr;
import org.drools.drl.ast.descr.RelationalExprDescr;
import org.drools.drl.parser.DrlExprParser;
import org.junit.Test;
import org.kie.internal.builder.conf.LanguageLevelOption;

public class DroolsTest {

    @Test
    public void testRules() {
        DrlExprParser exprParser = new DrlExprParser(LanguageLevelOption.DRL6);
        ConstraintConnectiveDescr constraintConnectiveDescr = exprParser.parse("value1 == 1 || value2 == 2");
        List<BaseDescr> childDescrs = constraintConnectiveDescr.getDescrs();
        ...


        displayDescr(constraintConnectiveDescr);
    }

    private void displayDescr(BaseDescr descr) {
        System.out.println(descr.getClass() + " : " + descr.toString());
        if (descr instanceof ConstraintConnectiveDescr constraintConnectiveDescr) {
            constraintConnectiveDescr.getDescrs().forEach(this::displayDescr);
        } else if (descr instanceof RelationalExprDescr relationalExprDescr) {
            displayDescr(relationalExprDescr.getLeft());
            displayDescr(relationalExprDescr.getRight());
        }
    }
}
