package com.threeti.ics.server.dao;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.mobile.ServiceToken;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "serviceTokenDao")
public class ServiceTokenDaoImpl implements ServiceTokenDao {
    @Override
    public ServiceToken add(ServiceToken serviceToken) {
        Jedis conn = JedisConnection.getResource();
        if (conn.get("serviceToken:" + serviceToken.getToken() + ":id") != null) {
            serviceToken.setId(Long.valueOf(conn.get("serviceToken:" + serviceToken.getToken() + ":id")));
            return serviceToken;
        }
        Long id = conn.incr("serviceToken.next.id");
        serviceToken.setId(id);
        conn.set("serviceToken:" + id + ":id", serviceToken.getApp().getAppKey());
        conn.set("serviceToken:" + id + ":appKey", serviceToken.getApp().getAppKey());
        conn.set("serviceToken:" + id + ":token", serviceToken.getToken());
        conn.set("serviceToken:" + id + ":appKey", serviceToken.getApp().getAppKey());
        conn.set("serviceToken:" + id + ":appName", serviceToken.getApp().getAppName());
        conn.set("serviceToken:" + id + ":uid", serviceToken.getDevice().getUid());
        conn.set("serviceToken:" + serviceToken.getToken() + ":id", id.toString());
        conn.save();
        JedisConnection.close(conn);
        return serviceToken;
    }

    @Override
    public String getUid(String token) {
        Jedis conn = JedisConnection.getResource();
        String result = conn.get("serviceToken:" + token + ":uid");
        JedisConnection.close(conn);
        return result;
    }

    @Override
    public String getAppName(String token) {
        Jedis conn = JedisConnection.getResource();
        String result = conn.get("serviceToken:" + token + ":appName");
        JedisConnection.close(conn);
        return result;
    }

    @Override
    public String getAppKey(String token) {
        Jedis conn = JedisConnection.getResource();
        String result = conn.get("serviceToken:" + token + ":appKey");
        JedisConnection.close(conn);
        return result;
    }


}
