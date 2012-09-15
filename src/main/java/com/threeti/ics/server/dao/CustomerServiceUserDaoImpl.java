package com.threeti.ics.server.dao;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.protocoldefinition.CustomerServiceStatus;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUserRole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "customerServiceUserDao")
public class CustomerServiceUserDaoImpl implements CustomerServiceUserDao {
    public void add(CustomerServiceUser user) {
        Jedis conn = JedisConnection.getResource();
        Long id = conn.incr("customerServiceUser.next.id");
        final String keyPrefix = "customerServiceUser:" + id.toString();
        conn.set(keyPrefix + ":id", id.toString());
        conn.set(keyPrefix + ":userName", user.getUserName());
        conn.set(keyPrefix + ":password", user.getPassword());
        conn.set(keyPrefix + ":serviceToken", user.getServiceToken());
        conn.set(keyPrefix + ":status", user.getStatus().toString());
        conn.set(keyPrefix + ":headPortrait", user.getHeadPortrait());
        conn.set(keyPrefix + ":nickName", user.getNickName());
        conn.set(keyPrefix + ":role", user.getRole().toString());
        conn.set("customerServiceUser:" + user.getServiceToken() + ":id", id.toString());
        conn.set("customerServiceUser:" + user.getUserName() + ":id", id.toString());
        conn.save();
        JedisConnection.close(conn);
    }

    public CustomerServiceUser get(final String userName) {
        Jedis conn = JedisConnection.getResource();
        String id = conn.get("customerServiceUser:" + userName + ":id");
        final String keyPrefix = "customerServiceUser:" + id;
        CustomerServiceUser result = new CustomerServiceUser();
        result.setNickName(conn.get(keyPrefix + ":nickName"));
        result.setRole(CustomerServiceUserRole.valueOf(conn.get(keyPrefix + ":role")));
        result.setServiceToken(conn.get(keyPrefix + ":serviceToken"));
        result.setStatus(CustomerServiceStatus.valueOf(conn.get(keyPrefix + ":status")));
        result.setHeadPortrait(conn.get(keyPrefix + ":headPortrait"));
        result.setPassword(conn.get(keyPrefix + ":password"));
        result.setUserName(userName);
        conn.save();
        JedisConnection.close(conn);
        return result;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\working\\projects\\ics\\web\\WEB-INF\\applicationContext.xml");
        CustomerServiceUserDao dao = ctx.getBean("customerServiceUserDao", CustomerServiceUserDao.class);
        CustomerServiceUser app = dao.get("abcdef0001");
        System.out.println(app.getNickName());
//        app.setPassword("123456");
//        CustomerServiceUser app= new CustomerServiceUser();
//        app.setPassword("123456");
//        app.setUserName("abcdef0000");
//        app.setStatus(CustomerServiceStatus.AVAILABLE);
//        app.setHeadPortrait("www.3ti.us/pics/3.jpg");
//        app.setVisitor("3783299222902037eddss");
//        app.setNickName("Jack");
//        app.setRole(CustomerServiceUserRole.ADMIN);
//        dao.add(app);

    }

}
