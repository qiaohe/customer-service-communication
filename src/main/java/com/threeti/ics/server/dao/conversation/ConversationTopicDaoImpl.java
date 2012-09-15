package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.protocoldefinition.ConversationTopic;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "conversationTopicDao")
public class ConversationTopicDaoImpl implements ConversationTopicDao {
    @Override
    public ConversationTopic add(ConversationTopic topic) {
        Jedis conn = JedisConnection.getResource();
        Long id = conn.incr("conversationTopic.next.id");
        final String keyPrefix = "conversationTopic:" + id.toString();
        topic.setId(id);
        if (!hasProductId(topic.getProductId())) {
            conn.set(keyPrefix + ":id", id.toString());
            conn.set("conversationTopic:" + topic.getProductId() + ":id", topic.getProductId());
            topic.setId(Long.valueOf(conn.get("conversationTopic:" + topic.getProductId() + ":id")));
        }
        conn.set(keyPrefix + ":productId", topic.getProductId());
        conn.set(keyPrefix + ":productName", topic.getProductName());
        conn.set(keyPrefix + ":description", topic.getDescription());
        conn.set(keyPrefix + ":picture", topic.getPicture());
        conn.set("conversationTopic:" + topic.getProductId() + ":id", id.toString());
        conn.save();
        JedisConnection.close(conn);
        return topic;
    }

    @Override
    public ConversationTopic get(String productId) {
        return null;
    }

    @Override
    public boolean hasProductId(String productId) {
        Jedis conn = JedisConnection.getResource();
        boolean result = conn.get("conversationTopic" + productId + ":id") == null;
        JedisConnection.close(conn);
        return result;
    }

    @Override
    public ConversationTopic save(ConversationTopic topic) {
        return null;
    }
}
