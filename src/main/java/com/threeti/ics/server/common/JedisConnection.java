package com.threeti.ics.server.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public final class JedisConnection {
    private static final String HOST = "192.168.1.200";
    private static final JedisPool POOL;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(5000);
        config.setMaxIdle(5000);
        config.setMaxWait(10000);
        config.setTestOnBorrow(true);
        POOL = new JedisPool(config, HOST, 6379, 1000000);
    }

    public static Jedis getResource() {
        return POOL.getResource();
    }

    public static void close(Jedis jedis) {
        POOL.returnResource(jedis);
    }

}
