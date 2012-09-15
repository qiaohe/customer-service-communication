package com.threeti.ics.server.domain.icsqueue;

import com.threeti.ics.server.domain.protocoldefinition.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class ActionRequiredQueue {
    public static final ConcurrentHashMap<String, List<Conversation>> SESSION_QUEUES = new ConcurrentHashMap<>();
    private static final ActionRequiredQueue INSTANCE = new ActionRequiredQueue();

    private ActionRequiredQueue() {
    }

    public static ActionRequiredQueue getInstance() {
        return INSTANCE;
    }

    public void add(final String serviceToken, List<Conversation> cons) {
        SESSION_QUEUES.put(serviceToken, cons);
    }

    public void add(final String serviceToken, Conversation con) {
        if (!SESSION_QUEUES.containsKey(serviceToken)) {
            List<Conversation> cs = new ArrayList<>();
            cs.add(con);
            SESSION_QUEUES.put(serviceToken, cs);
        } else {
            SESSION_QUEUES.get(serviceToken).add(con);
        }
    }
}
