package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.domain.protocoldefinition.message.Message;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public interface MessageDao {
    public void add(Message message);
}
