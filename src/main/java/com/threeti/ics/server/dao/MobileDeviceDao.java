package com.threeti.ics.server.dao;

import com.threeti.ics.server.domain.mobile.MobileDevice;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public interface MobileDeviceDao {
    public MobileDevice add(MobileDevice device);

    public MobileDevice get(String uid);

    public MobileDevice save(MobileDevice device);
}
