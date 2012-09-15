package com.threeti.ics.server.dao;

import com.threeti.ics.server.domain.mobile.ServiceToken;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceTokenDao {
    public ServiceToken add(ServiceToken serviceToken);

    public String getUid(String token);

    public String getAppName(String token);

    public String getAppKey(String token);

}
