package com.threeti.ics.server.dao;

import com.threeti.ics.server.common.JedisConnection;
import com.threeti.ics.server.domain.mobile.MobileDevice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "mobileDeviceDao")
public class MobileDeviceDaoImpl implements MobileDeviceDao {
    @Override
    public MobileDevice add(MobileDevice device) {
        try {
            Jedis conn = JedisConnection.getResource();
            String id = conn.get("mobileDevice:" + device.getUid() + ":id") == null ? conn.incr("mobileDevice.next.id").toString() : conn.get("mobileDevice:" + device.getUid() + ":id");
            device.setId(Long.valueOf(id));
            conn.set("mobileDevice:" + id + ":id", id);
            conn.set("mobileDevice:" + id + ":cpu", device.getCpu());
            conn.set("mobileDevice:" + id + ":model", device.getModel());
            conn.set("mobileDevice:" + id + ":network", device.getNetwork());
            conn.set("mobileDevice:" + id + ":uid", device.getUid());
            conn.set("mobileDevice:" + id + ":resolution", device.getResolution());
            conn.set("mobileDevice:" + device.getUid() + ":id", id);
            conn.save();
            JedisConnection.close(conn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return device;
    }

    @Override
    public MobileDevice get(String uid) {
        try {
            Jedis conn = JedisConnection.getResource();
            Long id = Long.valueOf(conn.get("mobileDevice:" + uid + ":id"));
            MobileDevice result = new MobileDevice();
            result.setId(id);
            result.setModel(conn.get("mobileDevice:" + id + ":model"));
            result.setNetwork(conn.get("mobileDevice:" + id + ":network"));
            result.setResolution(conn.get("mobileDevice:" + id + ":resolution"));
            result.setUid(conn.get("mobileDevice:" + id + ":uid"));
            result.setCpu(conn.get("mobileDevice:" + id + ":cpu"));
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public MobileDevice save(MobileDevice device) {
        Jedis conn = JedisConnection.getResource();
        Long id = device.getId();
        conn.set("mobileDevice:" + id + ":id", id.toString());
        conn.set("mobileDevice:" + id + ":cpu", device.getCpu());
        conn.set("mobileDevice:" + id + ":model", device.getModel());
        conn.set("mobileDevice:" + id + ":network", device.getNetwork());
        conn.set("mobileDevice:" + id + ":uid", device.getUid());
        conn.set("mobileDevice:" + id + ":resolution", device.getResolution());
        conn.set("mobileDevice:uid:" + device.getUid(), id.toString());
        conn.save();
        JedisConnection.close(conn);
        return device;
    }

    public static void main(String[] args) {
//        MobileDeviceDaoImpl dao = new MobileDeviceDaoImpl();
//        MobileDevice dm = new MobileDevice();
//        dm.setCpu("ARM 17");
//        dm.setModel("Apple");
//        dm.setUid("10100103091exaaa");
//        dm.setNetwork("2G");
//        dm.setResolution("1024X768");
//        dao.add(dm);
//        MobileDevice md = dao.get("10100103091exaaa");
//        System.out.println(md.getResolution());
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\working\\projects\\ics\\web\\WEB-INF\\applicationContext.xml");
        System.out.println(ctx.getBean("mobileDeviceDao"));


    }

}
