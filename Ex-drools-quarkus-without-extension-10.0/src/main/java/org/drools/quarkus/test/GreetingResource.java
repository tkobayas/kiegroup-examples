package org.drools.quarkus.test;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.drools.model.codegen.ExecutableModelProject;
import org.jboss.logging.Logger;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

@Path("test")
public class GreetingResource {

	private static final Logger LOG = Logger.getLogger(GreetingResource.class);

	@GET
    @Path("/rule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshEngine(){

		// This method intentionally builds rules every time
		// In a real application, you should have separate methods to build rules and to execute them

		final KieServices kieServices = KieServices.Factory.get();

		ReleaseId releaseId = kieServices.newReleaseId("com.sample", "my-sample", "1.0.0");

		final KieFileSystem kieFileSystem = getKieFileSystem(kieServices, releaseId);
		kieServices.newKieBuilder(kieFileSystem).buildAll(ExecutableModelProject.class);
		final KieContainer kContainer = kieServices.newKieContainer(releaseId);
		final KieBase kieBase = kContainer.getKieBase();
		LOG.info(kieBase.getKiePackages());
		for (final KiePackage kp : kieBase.getKiePackages() ) {
			LOG.info(kp.getRules());
			for (final Rule rule : kp.getRules()) {
				LOG.info("       kp " + kp + " rule " + rule.getName());
			}
		}
		try (final KieSession session = kieBase.newKieSession()) {
			final Person person = new Person("John", 20);
			session.insert(person);
			session.fireAllRules();
			return Response.ok("rule done : person = " + person , MediaType.APPLICATION_JSON).build();
		}
    }

	private KieFileSystem getKieFileSystem(final KieServices kieServices, ReleaseId releaseId) {
		final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		final String drl = """
					package org.drools.drl
					
					import org.drools.quarkus.test.Person
					
					rule R1 when
							$p : Person( age >= 18 )
						then
							$p.setAdult(true);
					end
                """;
		kieFileSystem.write("src/main/resources/org/drools/drl/sample.drl", drl);
		kieFileSystem.generateAndWritePomXML(releaseId);

		return kieFileSystem;
	}
}
