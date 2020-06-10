package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();
            //kSession.getEnvironment().set("org.jbpm.rule.task.waitstate", true);
            
            // go !
            Message message = new Message();
            kSession.insert(message);
            kSession.startProcess("com.sample.bpmn.hello.flow");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        private int count = 0;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Message [count=" + count + "]";
        }

    }

}
