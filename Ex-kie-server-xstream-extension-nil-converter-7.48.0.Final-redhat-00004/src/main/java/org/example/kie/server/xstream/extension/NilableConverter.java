package org.example.kie.server.xstream.extension;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NilableConverter implements Converter {

    private static final Logger LOG = LoggerFactory.getLogger(NilableConverter.class);

    @Override
    public boolean canConvert(Class type) {
        return String.class.equals(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        // This method is not actually called because AbstractReflectionConverter.doMarshal() checks null earlier (line.152)
        if (source == null) {
            writer.addAttribute("nil", "true");
        } else {
            writer.setValue(source.toString());
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String value = ""; // default value
        if ("true".equals(reader.getAttribute("nil"))) {
            value = null;
        } else {
            value = reader.getValue();
        }
        return value;
    }

}
