package com.sample;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DecisionTableTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();

            // useful for debug
            SpreadsheetCompiler compiler = new SpreadsheetCompiler();
            String drl = compiler.compile(ks.getResources().newClassPathResource("com/sample/Sample.xls").getInputStream(), InputType.XLS);
            System.out.println(drl);

            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();

            // go !
            Person p1 = new Person("John", 10);
            Person p2 = new Person("John", 30);

            kSession.insert(p1);
            kSession.insert(p2);

            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
