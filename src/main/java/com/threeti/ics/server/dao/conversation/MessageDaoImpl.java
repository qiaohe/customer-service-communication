package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "messageDao")
public class MessageDaoImpl implements MessageDao {
    @Override
    public void add(Message message) {
        Jedis conn = JedisConnection.getResource();
        Long id = conn.incr("message.next.id");
        String cid = conn.get("conversation:topic:productId:" + message.getTopic() + ":visitor:" + message.getFrom() + ":id");
        final String keyPrefix = "topic:" + message.getTopic() + ":conversation:" + cid + ":message"; //+ id.toString();
        message.setId(id);
        conn.lpush(keyPrefix, ObjectJsonMapper.getJsonStringBy(message));


//        conn.set(keyPrefix + ":id", id.toString());
//        conn.set(keyPrefix + ":conversation:id", cid);
//        conn.set(keyPrefix + ":from", message.getFrom());
//        conn.set(keyPrefix + ":to", StringUtils.isEmpty(message.getTo()) ? "" : message.getTo());
//        conn.set(keyPrefix + ":replyTo", StringUtils.isEmpty(message.getReplyTo()) ? "" : message.getReplyTo());
//        conn.set(keyPrefix + ":messageBody", message.getMessageBody());
//        conn.set(keyPrefix + ":topic", message.getTopic());
//        conn.set(keyPrefix + ":version", StringUtils.isEmpty(message.getVersion()) ? "" : message.getVersion());
//        conn.set(keyPrefix + ":date", message.getDate().toString());
//        conn.set(keyPrefix + ":status", message.getStatus().toString());
//        for (Map.Entry<String, String> h : message.getHeader().entrySet()) {
//            conn.hset(keyPrefix + ":status", h.getKey(), h.getValue());
//        }
        conn.save();
        JedisConnection.close(conn);
    }
}
