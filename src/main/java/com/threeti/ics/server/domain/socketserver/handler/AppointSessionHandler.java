package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class AppointSessionHandler implements SessionHandler {
    @Autowired
    private ConversationDao conversationDao;

    @Override
    public void handle(SessionOperationRequest request, IoSession session) {
        conversationDao.doAccept(request);
        for (IoSession so : SessionManager.getInstance().getCustomerServiceSessions(session)) {
            CommonObject co = new CommonObject();
            co.setType("005");
            co.addProperty("operationType", request.getOperationType());
            co.addProperty("customerServiceUserName", request.getCustomerServiceUserName());
            co.addProperty("productId", request.getConversationKeys().get(0).getProductId());
            co.addProperty("visitor", request.getConversationKeys().get(0).getVisitor());
            so.write(co.toJson());
        }
    }
}
