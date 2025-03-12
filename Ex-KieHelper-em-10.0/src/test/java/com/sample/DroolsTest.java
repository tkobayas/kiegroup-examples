package com.sample;

import org.drools.model.codegen.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

public class DroolsTest {

    @Test
    public void testRules() {

        String drl = """
                package com.sample
                import com.sample.Person;
                rule Hello1
                  when
                    $p : Person(age > 20 || age < 10)
                  then
                    System.out.println("Hello, " + $p.getName());
                end
                """;

        // KieHelper is a convenient util for test purpose
        // Use kjar or KieFileSystem for production
        KieBase kBase = new KieHelper().addContent(drl, ResourceType.DRL).build(ExecutableModelProject.class);
        KieSession ksession = kBase.newKieSession();

        try {
            ksession.insert(new Person("John", 35));
            ksession.insert(new Person("Paul", 18));

            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }
    }
}
