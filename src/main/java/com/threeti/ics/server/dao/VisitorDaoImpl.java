package com.threeti.ics.server.dao;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.protocoldefinition.identity.Visitor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "visitorDao")
public class VisitorDaoImpl implements VisitorDao {
    @Override
    public void add(Visitor visitor) {
        Jedis conn = JedisConnection.getResource();
        Long id = conn.incr("visitor.next.id");
        conn.set("visitor:" + id + ":id", id.toString());
        final String name = "V" + conn.get("serviceToken:" + visitor.getServiceToken() + ":id");
        conn.set("visitor:" + id + ":name", name);
        conn.set("visitor:" + id + ":serviceToken", visitor.getServiceToken());
        conn.set("visitor:" + visitor.getName() + ":id", visitor.getId().toString());
        conn.save();
        JedisConnection.close(conn);
    }
}
