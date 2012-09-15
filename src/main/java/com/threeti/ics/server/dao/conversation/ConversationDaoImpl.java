package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.ConversationStatus;
import com.threeti.ics.server.domain.protocoldefinition.ConversationTopic;
import com.threeti.ics.server.domain.protocoldefinition.SessionOperationType;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "conversationDao")
public class ConversationDaoImpl implements ConversationDao {
    @Override
    public void add(Conversation conversation) {
        Jedis conn = JedisConnection.getResource();
        String id = conn.get("conversation:topic:productId:" + conversation.getTopic().getProductId() + ":visitor:" + conversation.getVisitor() + ":id");
        if (id == null) {
            id = String.valueOf(conn.incr("conversation.next.id"));
            conversation.setId(Long.valueOf(id));
            conn.lpush("publicqueue", id);
        }
        final String keyPrefix = "conversation:" + id;
        conn.set(keyPrefix + ":id", id);
        conn.set(keyPrefix + ":topic", conversation.getTopic().getId().toString());
        conn.set(keyPrefix + ":visitor", conversation.getVisitor());
        conn.set(keyPrefix + ":visitorName", "V" + conn.get("serviceToken:" + conversation.getVisitor() + ":id"));
        conn.set(keyPrefix + ":customerServiceUser", conversation.getCustomerServiceUser() == null ? "" : conversation.getCustomerServiceUser().getId().toString());
        conn.set(keyPrefix + ":status", conversation.getStatus().toString());
        conn.set(keyPrefix + ":createDate", conversation.getCreateDate().toString());
        conn.set("conversation:topic:productId:" + conversation.getTopic().getProductId() + ":visitor:" + conversation.getVisitor() + ":id", id);
        conn.save();
        JedisConnection.close(conn);
    }

    @Override
    public List<Conversation> get(String customerServiceUser) {
        Jedis conn = JedisConnection.getResource();
        List<String> list = conn.lrange("publicqueue", 0, 10);
        List<Conversation> result = new ArrayList<>();
        for (String index : list) {
            final String keyPrefix = "conversation:" + index;
            Conversation c = new Conversation();
            c.setId(Long.valueOf(index));
            c.setCreateDate(new Date());
            c.setStatus(ConversationStatus.NOTACCEPTED);
            String topicId = conn.get(keyPrefix + ":topic");
            ConversationTopic topic = new ConversationTopic();
            topic.setId(Long.valueOf(topicId));
            topic.setProductId(conn.get("conversationTopic:" + topicId + ":productId"));
            topic.setProductName(conn.get("conversationTopic:" + topicId + ":productName"));
            topic.setPicture(conn.get("conversationTopic:" + topicId + ":picture"));
            topic.setDescription(conn.get("conversationTopic:" + topicId + ":description"));

            c.setTopic(topic);
            c.setVisitor(conn.get(keyPrefix + ":visitor"));
            result.add(c);
        }
        JedisConnection.close(conn);
        return result;
    }

    @Override
    public List<Conversation> get() {
        Jedis conn = JedisConnection.getResource();
        List<String> list = conn.lrange("publicqueue", 0, 10);
        List<Conversation> result = new ArrayList<>();
        for (String index : list) {
            String keyPrefix = "conversation:" + index;
            Conversation c = new Conversation();
            c.setId(Long.valueOf(index));
            c.setCreateDate(new Date());
            c.setVisitorName(conn.get(keyPrefix + ":visitorName"));
            c.setVisitor(conn.get(keyPrefix + ":visitor"));
            c.setStatus(ConversationStatus.NOTACCEPTED);
            String topicId = conn.get(keyPrefix + ":topic");
            ConversationTopic topic = new ConversationTopic();
            topic.setId(Long.valueOf(topicId));
            String productId = conn.get("conversationTopic:" + topicId + ":productId");
            topic.setProductId(productId);
            topic.setProductName(conn.get("conversationTopic:" + topicId + ":productName"));
            topic.setPicture(conn.get("conversationTopic:" + topicId + ":picture"));
            topic.setDescription(conn.get("conversationTopic:" + topicId + ":description"));

            c.setTopic(topic);
            c.setVisitor(conn.get(keyPrefix + ":visitor"));
            String quenename = "topic:" + c.getTopic().getProductId() + ":conversation:" + c.getId() + ":message"; //+ id.toString();
            for (String s : conn.lrange(quenename, 0, 10)) {
                c.addMessage(ObjectJsonMapper.getObjectBy(s, Message.class));
            }
            result.add(c);
        }
        JedisConnection.close(conn);
        return result;
    }

    @Override
    public void doSuspend(SessionOperationRequest request) {
        Jedis conn = JedisConnection.getResource();
        final String productId = request.getConversationKeys().get(0).getProductId();
        final String visitor = request.getConversationKeys().get(0).getVisitor();


        String cid = conn.get("conversation:topic:productId:" + productId + ":visitor:" + visitor + ":id");
        conn.lrem("sessionqueue:" + request.getCustomerServiceUserName(), 1, cid);
        conn.lpush("actionrequiredqueue:" + request.getCustomerServiceUserName(), cid);
        conn.save();
        JedisConnection.close(conn);
    }

    @Override
    public void doAccept(SessionOperationRequest request) {
        Jedis conn = JedisConnection.getResource();
        final String productId = request.getConversationKeys().get(0).getProductId();
        final String visitor = request.getConversationKeys().get(0).getVisitor();
        String cid = conn.get("conversation:topic:productId:" + productId + ":visitor:" + visitor + ":id");
        conn.lpush("sessionqueue:" + request.getCustomerServiceUserName(), cid);
        conn.lrem("publicqueue", 0, cid);
        conn.save();
        JedisConnection.close(conn);
    }

    @Override
    public void terminate(SessionOperationRequest request) {
        Jedis conn = JedisConnection.getResource();
        final String productId = request.getConversationKeys().get(0).getProductId();
        final String visitor = request.getConversationKeys().get(0).getVisitor();
        String cid = conn.get("conversation:topic:productId:" + productId + ":visitor:" + visitor + ":id");
        if (request.isFromSessionQueueWithinTerminate()) {
            conn.lrem("sessionqueue:" + request.getCustomerServiceUserName(), 0, cid);
        } else {
            conn.lrem("actionrequiredqueue:" + request.getCustomerServiceUserName(), 0, cid);
        }
        conn.save();
        JedisConnection.close(conn);
    }

    @Override
    public void dpAppoint(SessionOperationRequest request) {
        Jedis conn = JedisConnection.getResource();
        final String productId = request.getConversationKeys().get(0).getProductId();
        final String visitor = request.getConversationKeys().get(0).getVisitor();
        String cid = conn.get("conversation:topic:productId:" + productId + ":visitor:" + visitor + ":id");
        conn.lpush("sessionqueue:" + request.getCustomerServiceUserName(), cid);
        conn.lrem("publicqueue", 0, cid);
        conn.save();
        JedisConnection.close(conn);

    }

    @Override
    public void resume(SessionOperationRequest request) {
        Jedis conn = JedisConnection.getResource();
        final String productId = request.getConversationKeys().get(0).getProductId();
        final String visitor = request.getConversationKeys().get(0).getVisitor();
        String cid = conn.get("conversation:topic:productId:" + productId + ":visitor:" + visitor + ":id");
        conn.lpush("sessionqueue:" + request.getCustomerServiceUserName(), cid);
        conn.lrem("actionrequiredqueue:" + request.getCustomerServiceUserName(), 0, cid);
        conn.save();
        JedisConnection.close(conn);

    }

    @Override
    public HashMap<String, Long> getTotalSummary(String customerServiceUserName) {
        HashMap<String, Long> result = new HashMap<>();
        Jedis conn = JedisConnection.getResource();
        result.put("publicqueue", conn.llen("publicqueue"));
        result.put("sessionqueue", conn.llen("sessionqueue:" + customerServiceUserName));
        result.put("actionrequiredqueue", conn.llen("actionrequiredqueue:" + customerServiceUserName));
        result.put("customermessage", conn.llen("customermessage"));
        JedisConnection.close(conn);
        return result;
    }

    public static void main(String[] args) {
//        Jedis conn = JedisConnection.getResource();
//        for (String key : conn.keys("actionrequiredqueue*")) {
//            conn.del(key);
//        }
//        conn.lrem("publicqueue", 1, "2");
//
//        conn.save();
//        JedisConnection.close(conn);
        System.out.println(SessionOperationType.ACCEPT.name());

    }
}
