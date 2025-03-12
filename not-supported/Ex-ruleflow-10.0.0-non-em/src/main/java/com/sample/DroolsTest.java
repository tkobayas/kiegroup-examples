package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // This main works with non-exec-model
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();
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
