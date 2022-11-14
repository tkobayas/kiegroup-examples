package com.sample;


import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

public class RoundTripTest {

    @Test
    public void roundTrip() {
        Set<Class<?>> extraClasses = new HashSet<Class<?>>();
        extraClasses.add(Person.class);
        Marshaller marshaller = MarshallerFactory.getMarshaller(extraClasses, MarshallingFormat.XSTREAM, getClass().getClassLoader());

        Person person = new Person(null, 35);

        String payload = marshaller.marshall(person);

        System.out.println("--- payload");
        System.out.println(payload);

        Person person2 = marshaller.unmarshall(payload, Person.class);
        System.out.println();
        System.out.println("--- unmarshalled value");
        System.out.println(person2);
    }
}
