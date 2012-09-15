package com.threeti.ics.server.domain.icsqueue;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.dto.ConversationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class PublicQueue {
    private static final PublicQueue INSTANCE = new PublicQueue();
    private static final List<Conversation> CONS = new CopyOnWriteArrayList<>();

    private PublicQueue() {
    }

    public static PublicQueue getInstance() {
        return INSTANCE;
    }

    public void add(Conversation con) {
        if (!CONS.contains(con)) {
            CONS.add(con);
        }
    }

    public List<Conversation> getAll() {
        return CONS;
    }

    public void remove(Conversation con) {
        if (CONS.contains(con)) {
            CONS.remove(con);
        }
    }

    public int size() {
        return CONS.size();
    }

    public Conversation getConversation(final String serviceToken, final String productId) {
//        for (Conversation c : getAll()) {
//            if (c.getVisitor().getVisitor().equals(serviceToken) && c.getTopic().getProductId().equals(productId)) {
//                return c;
//            }
//        }
        return null;
    }
    public void addMessage(Message message) {
        Conversation c = getConversation(message.getFrom(), message.getTopic());
        if (c != null) {
            c.addMessage(message);
        }
    }

    public List<Conversation> getConversations(QueueRequest queueRequest) {
        return CONS.subList(queueRequest.getFrom(), queueRequest.getFrom() + queueRequest.getSize());
    }

    public String toJson() {
//        List<ConversationDto> result = new ArrayList<>();
//        for (Conversation c : CONS) {
//            ConversationDto dto = new ConversationDto(c.getVisitor().getVisitor(), c.getTopic(), c.getMessages());
//            result.add(dto);
//        }
//        return ObjectJsonMapper.getJsonStringBy(result);
        return null;
    }
}
