package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        ksession.insert(new Person("John", 35));
        ksession.fireAllRules(10);
        ksession.dispose();

    }
    
    public static int myfunc(String str) {
        System.out.println("myfunc");
        return 0;
    }
}
