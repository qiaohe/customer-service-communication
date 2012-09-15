package com.threeti.ics.server.dao;

import com.threeti.ics.server.domain.mobile.MobileApp;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public interface MobileAppDao {
    public MobileApp add(MobileApp app);

    public MobileApp save(MobileApp app);

    public MobileApp remove(MobileApp app);

    public MobileApp get(String appKey);
}
