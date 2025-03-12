import java.util.ArrayList;
import java.util.List;

import com.sample.Cheese;
import com.sample.Person;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TemplateTest {

    @Test
    public void testTemplateKjar() {
    try {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "template-kjar", "1.0.0-SNAPSHOT");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();
        
        List<String> list = new ArrayList<>();
        kSession.setGlobal("list", list);
        

        Person john = new Person("john", 42);
        Cheese stilton = new Cheese("stilton");

        kSession.insert(john);
        kSession.insert(stilton);

        kSession.fireAllRules();
        
        System.out.println(list);

        kSession.dispose();

    } catch (Throwable t) {
        t.printStackTrace();
    }
    }
}
