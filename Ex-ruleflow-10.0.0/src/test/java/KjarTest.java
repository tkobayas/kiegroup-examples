import com.sample.DroolsTest;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class KjarTest {

    @Test
    public void testKjar() {
        // This test works with exec-model which was built by mvn install
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "ruleflow-example", "1.0.0-SNAPSHOT");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();
        DroolsTest.Message message = new DroolsTest.Message();
        kSession.insert(message);
        kSession.startProcess("com.sample.bpmn.hello.flow");
    }
}