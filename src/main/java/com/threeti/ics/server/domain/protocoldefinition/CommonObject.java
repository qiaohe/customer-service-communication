package com.threeti.ics.server.domain.protocoldefinition;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public class CommonObject {
    private String type;
    private HashMap<String, Object> properties = new HashMap<>();

    public CommonObject() {
    }

    public CommonObject(String type, HashMap<String, Object> properties) {
        this();
        this.type = type;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("data")
    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(final String key, final Object value) {
        properties.put(key, value);
    }

    public void removeProperty(final String key, final Object value) {
        if (properties.containsKey(key)) {
            properties.remove(key);
        }
    }

    public int propCount() {
        return properties.size();
    }

    public String toJson() {
        return ObjectJsonMapper.getJsonStringBy(this);

    }

    public static void main(String[] args) {
    }
}
