package com.threeti.ics.server.domain.icsqueue;

import com.threeti.ics.server.domain.protocoldefinition.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class SessionQueue {
    public static final ConcurrentHashMap<String, List<Conversation>> SESSION_QUEUES = new ConcurrentHashMap<>();
    private static final SessionQueue INSTANCE = new SessionQueue();

    private SessionQueue() {
    }

    public static SessionQueue getInstance() {
        return INSTANCE;
    }

    public synchronized void add(final String serviceToken, List<Conversation> cons) {
        SESSION_QUEUES.put(serviceToken, cons);
    }


    public synchronized void add(final String serviceToken, Conversation con) {
        if (!SESSION_QUEUES.containsKey(serviceToken)) {
            List<Conversation> cs = new ArrayList<>();
            cs.add(con);
            SESSION_QUEUES.put(serviceToken, cs);
        } else {
            SESSION_QUEUES.get(serviceToken).add(con);
        }
    }

    public Conversation getConversation(String customerService, String visitor, String productId) {
//        List<Conversation> cs = SESSION_QUEUES.get(customerService);
//        for (Conversation c : cs) {
//            if (c.getVisitor().getVisitor().equals(visitor) && c.getTopic().getProductId().equals(productId)) {
//                return c;
//            }
//        }
        return null;


    }

    public synchronized void remove(String customerService, Conversation con) {
        List<Conversation> cs = SESSION_QUEUES.get(customerService);
        if (cs.contains(con)) {
            cs.remove(con);
        }
    }
}
