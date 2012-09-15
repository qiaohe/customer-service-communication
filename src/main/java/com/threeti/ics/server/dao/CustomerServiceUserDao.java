package com.threeti.ics.server.dao;

import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerServiceUserDao {
    public void add(CustomerServiceUser user);

    public CustomerServiceUser get(final String userName);
}
