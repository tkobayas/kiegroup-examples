package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.drools.ruleunits.api.RuleUnit;
import org.drools.ruleunits.api.RuleUnitInstance;

@Path("/hello")
public class GreetingResource {

    @Inject
    RuleUnit<HelloWorldUnit> ruleUnit;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        HelloWorldUnit unit = new HelloWorldUnit();
        unit.getStrings().add("Mario");

        try ( RuleUnitInstance<HelloWorldUnit> instance = ruleUnit.createInstance(unit)  ) {
            instance.fire();
        }

        return unit.getResults().toString();
    }
}
