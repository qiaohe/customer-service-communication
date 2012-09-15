package com.threeti.ics.server.dao;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.mobile.MobileApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 10:35
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "mobileAppDao")
public class MobileAppDaoImpl implements MobileAppDao {
    @Override
    public MobileApp add(MobileApp app) {
        Jedis conn = JedisConnection.getResource();
        Long id = conn.incr("mobileapp.next.id");
        final String keyPrefix = "mobileApp:" + id.toString();
        conn.set(keyPrefix + ":id", id.toString());
        conn.set(keyPrefix + ":appKey", app.getAppKey());
        conn.set(keyPrefix + ":appName", app.getAppName());
        conn.set("mobileApp:" + app.getAppKey() + ":id", id.toString());
        conn.save();
        app.setId(id);
        JedisConnection.close(conn);
        return app;
    }

    @Override
    public MobileApp save(MobileApp app) {
        Jedis conn = JedisConnection.getResource();
        final String keyPrefix = "mobileApp:" + app.getId();
        conn.set(keyPrefix + ":id", String.valueOf(app.getId()));
        conn.set(keyPrefix + ":appKey", app.getAppKey());
        conn.set(keyPrefix + ":appName", app.getAppName());
        conn.save();
        JedisConnection.close(conn);
        return null;
    }

    @Override
    public MobileApp remove(MobileApp app) {
        return null;
    }

    @Override
    public MobileApp get(String appKey) {
        Jedis conn = JedisConnection.getResource();
        MobileApp result = new MobileApp();
        String id = conn.get("mobileApp:" + appKey + ":id");
        result.setAppKey(appKey);
        result.setAppName(conn.get("mobileApp:" + id + ":appName"));
        result.setId(Long.valueOf(id));
        return result;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\working\\projects\\ics\\web\\WEB-INF\\applicationContext.xml");
        MobileAppDao dao = ctx.getBean("mobileAppDao", MobileAppDao.class);
        MobileApp app = new MobileApp();
        app.setAppKey("8890fgdkj90fdg89f88");
        app.setAppName("app2");
        dao.add(app);
    }
}
