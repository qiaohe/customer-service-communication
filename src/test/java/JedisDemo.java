import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 10/09/12
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class JedisDemo {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.1.101", 6379);
        Jedis jedis = pool.getResource();
        pool.returnResource(jedis);
        pool.destroy();
    }
}
