package org.example.kie.server.xstream.extension;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import org.kie.server.api.marshalling.xstream.XStreamMarshaller;
import org.kie.server.api.marshalling.xstream.XStreamMarshallerExtension;

public class NilableConverterXStreamMarshallerExtension implements XStreamMarshallerExtension {

    @Override
    public void extend(XStreamMarshaller marshaller) {
        XStream xstream = marshaller.getXstream();
        xstream.registerConverter(new NilableConverter());
        xstream.registerConverter(new NilableReflectionConverter(xstream.getMapper(), new PureJavaReflectionProvider()));
    }
}
