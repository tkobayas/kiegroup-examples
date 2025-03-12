package com.sample;

import java.beans.Introspector;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.mvel.ConstraintEvaluationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ComparisonTest extends BaseTest {

    private static final Class[] TYPES = new Class[]{Integer.class, Long.class, Byte.class, Character.class, Short.class, Float.class, Double.class, BigInteger.class, BigDecimal.class};
    private static final String[] COMPARISON_OPERATORS = new String[]{"==", "!=", "<", ">", "<=", ">="};
    private static final boolean[] PROPERTY_ON_LEFT = new boolean[]{true, false};

    @Parameterized.Parameters(name = "{0} {1} {2}")
    public static Collection<Object[]> params() {
        List<Object[]> parameterData = new ArrayList<Object[]>();
        for (Class type : TYPES) {
            for (String operator : COMPARISON_OPERATORS) {
                for (boolean propertyOnLeft : PROPERTY_ON_LEFT)
                    parameterData.add(new Object[]{type, operator, propertyOnLeft});
            }
        }
        return parameterData;
    }

    @Parameterized.Parameter(0)
    public Class type;

    @Parameterized.Parameter(1)
    public String operator;

    @Parameterized.Parameter(2)
    public boolean propertyOnLeft;

    @Test
    public void operatorsWithNull() throws Exception {
        String propertyName = getPropertyName(type);
        String instanceValueString = getInstanceValueString(type);
        String drl = "import " + type.getCanonicalName() + ";\n" +
                     "import " + ValueHolder.class.getCanonicalName() + ";\n" +
                     "rule R\n" +
                     "when\n";
        if (propertyOnLeft) {
            drl += "  ValueHolder(" + propertyName + " " + operator + " " + instanceValueString + ")\n";
        } else {
            drl += "  ValueHolder(" + instanceValueString + " " + operator + " " + propertyName + ")\n";
        }
        drl += "then\n" +
               "end\n";

        System.out.println(drl);
        KieSession ksession = getKieSession(drl);
        ksession.insert(new ValueHolder());
        try {
            int fired = ksession.fireAllRules();
            System.out.println("fired = " + fired);
            fail("Should throw NPE");
        } catch (ConstraintEvaluationException | NullPointerException e) {
            System.out.println("OK : " + e.getMessage());
            assertTrue(true);
        }
    }

    private String getPropertyName(Class clazz) {
        return Introspector.decapitalize(clazz.getSimpleName()) + "Value";
    }

    private String getInstanceValueString(Class clazz) {
        return "new " + clazz.getSimpleName() + "(\"0\")";
    }
}
