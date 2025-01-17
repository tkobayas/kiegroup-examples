package org.drools.quarkus.test;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kie.api.runtime.KieRuntimeBuilder;
import org.kie.api.runtime.KieSession;

@Path("test")
public class GreetingResource {

	// This is traditional KIE API approach instead of RuleUnit
	@Inject
	KieRuntimeBuilder runtimeBuilder;

	@GET
    @Path("/rule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshEngine(){
        System.out.println("running rule");

		KieSession kieSession = runtimeBuilder.newKieSession();
		Result result = new Result();
		Person person = new Person("John", 20);
		kieSession.insert(result);
		kieSession.insert(person);
		kieSession.fireAllRules();
		kieSession.dispose();

		System.out.println(result.toString());

		return Response.ok("rule done : result = " + result.toString() , MediaType.APPLICATION_JSON).build();
    }
}
