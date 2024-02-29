
package org.example;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("10s")
public class Measurement {
    private String id;
    private String val;

    public Measurement(String id, String val) {
        super();
        this.id = id;
        this.val = val;
    }

    public String getId() {
        return id;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Measurement [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (val != null)
            builder.append("val=").append(val);
        builder.append("]");
        return builder.toString();
    }
}
