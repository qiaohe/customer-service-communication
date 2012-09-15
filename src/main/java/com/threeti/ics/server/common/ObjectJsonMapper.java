package com.threeti.ics.server.common;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 03/09/12
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public final class ObjectJsonMapper {
    private static final Logger logger = Logger.getLogger(ObjectJsonMapper.class);

    public static <T> T getObjectBy(final String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String getJsonStringBy(final Object o) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(o);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}


