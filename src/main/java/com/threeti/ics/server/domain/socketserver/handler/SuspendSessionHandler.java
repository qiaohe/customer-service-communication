package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.icsqueue.ActionRequiredQueue;
import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.icsqueue.SessionQueue;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SuspendSessionHandler implements SessionHandler {
    @Autowired
    private ConversationDao conversationDao;

    @Override
    public void handle(SessionOperationRequest request, IoSession session) {
        conversationDao.doSuspend(request);
        CommonObject co = new CommonObject();
        co.setType("005");
        co.addProperty("operationType", request.getOperationType().toString());
        co.addProperty("response", "ok");
        session.write(co.toJson());
    }
}
