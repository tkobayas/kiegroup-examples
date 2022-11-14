package org.example.kie.server.xstream.extension;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NilableReflectionConverter extends ReflectionConverter {

    private static final Logger LOG = LoggerFactory.getLogger(NilableReflectionConverter.class);

    public NilableReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public boolean canConvert(Class type) {
        return type != null && type.getCanonicalName().equals("com.sample.Person") && canAccess(type);
    }

    @Override
    protected void doMarshal(final Object source,
                             final HierarchicalStreamWriter writer,
                             final MarshallingContext context) {
        super.doMarshal(source, writer, context);
        addNilFields(source, writer);
    }

    private void addNilFields(Object source, HierarchicalStreamWriter writer) {
        Class clazz = source.getClass();
        try {
            BeanInfo info = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getName().equals("class")) {
                    continue;
                }
                try {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object value = readMethod.invoke(source);
                    if (value == null) {
                        writer.startNode(propertyDescriptor.getName());
                        writer.addAttribute("nil", "true");
                        writer.endNode();
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    LOG.warn("failed to get {}", propertyDescriptor.getName(), e);
                }
            }

        } catch (IntrospectionException e) {
            LOG.warn("failed to introspect {}", clazz.getName(), e);
        }
    }

    // No need to override doUnmarshal() because NilableConverter.unmarshal() does the job
}
