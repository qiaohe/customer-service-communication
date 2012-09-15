package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.conversation.MessageDao;
import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.protocoldefinition.message.MessageStatus;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class AcceptSessionHandler implements SessionHandler {
    @Autowired
    private ConversationDao conversationDao;
    @Autowired
    private MessageDao messageDao;

    public AcceptSessionHandler() {
    }

    @Override
    public void handle(SessionOperationRequest request, IoSession session) {
        try {
            conversationDao.doAccept(request);
            CustomerServiceUser customerServiceUser = (CustomerServiceUser) session.getAttribute("customerServiceUser");
            for (IoSession s : SessionManager.getInstance().getCustomerServiceSessions(session)) {
                    CommonObject co = new CommonObject();
                    co.setType("005");
                    co.addProperty("productId", request.getConversationKeys().get(0).getProductId());
                    co.addProperty("visitor", request.getConversationKeys().get(0).getVisitor());
                    co.addProperty("operationType", request.getOperationType().toString());
                    co.addProperty("customerServiceUser", customerServiceUser.getUserName());
                    s.write(co.toJson());
            }
            IoSession session1 = SessionManager.getInstance().getSession(session, request.getConversationKeys().get(0).getVisitor());
            Message msg = new Message();
            msg.setDate(new Date());
            msg.setFrom(request.getCustomerServiceUserName());
            msg.setHeader(new HashMap<String, String>());
            msg.setStatus(MessageStatus.SENT);
            msg.setMessageBody(String.format("客服%s开始为您服务", customerServiceUser.getNickName()));
            msg.setTo(request.getConversationKeys().get(0).getVisitor());
            msg.setVersion("1.0.0");
            msg.setTopic(request.getConversationKeys().get(0).getProductId());
            messageDao.add(msg);
            CommonObject co = new CommonObject();
            co.setType("003");
            co.addProperty("message", msg);
            session1.write(co.toJson());
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }


}

