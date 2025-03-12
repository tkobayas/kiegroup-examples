package com.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.drools.core.reteoo.ReteDumper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) throws Exception {

        System.setProperty("drools.dump.dir", "/home/tkobayas/tmp");
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

//        ReteDumper.dumpRete(ksession);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        InputData person = new InputData("ABC");
        Span span1 = new Span(sdf.parse("2020-01-01"), sdf.parse("2020-02-01"));
        Span span2 = new Span(sdf.parse("2020-03-01"), sdf.parse("2020-04-01"));
        Span span3 = new Span(sdf.parse("2020-01-15"), sdf.parse("2020-02-15"));
        List<Span> spanList = new ArrayList<>();
        spanList.add(span1);
        spanList.add(span2);
        spanList.add(span3);
        
        person.setSpanList(spanList);
        
        ksession.insert(person);

        ksession.fireAllRules();
        ksession.dispose();

        System.out.println("====");
    }

}
