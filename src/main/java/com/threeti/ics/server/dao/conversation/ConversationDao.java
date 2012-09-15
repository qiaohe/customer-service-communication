package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public interface ConversationDao {
    public void add(Conversation conversation);

    public List<Conversation> get(final String customerServiceUser);

    public List<Conversation> get();

    void doSuspend(SessionOperationRequest request);

    void doAccept(SessionOperationRequest request);

    void terminate(SessionOperationRequest request);

    void dpAppoint(SessionOperationRequest request);

    void resume(SessionOperationRequest request);

    HashMap<String, Long> getTotalSummary(String customerServiceUserName);
}
