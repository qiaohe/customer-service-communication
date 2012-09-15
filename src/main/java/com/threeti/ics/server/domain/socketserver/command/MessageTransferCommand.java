package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.MessageDao;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class MessageTransferCommand extends AbstractCommand implements Command {
    @Autowired
    private MessageDao messageDao;

    public MessageTransferCommand(Object request) {
        super(request);
    }


    @Override
    public void execute(IoSession session) {
        final Message msg = ObjectJsonMapper.getObjectBy(getRequestAsString(), Message.class);
        messageDao.add(msg);
        if (!StringUtils.isEmpty(msg.getTo())) {
            CommonObject co = new CommonObject();
            co.setType("003");
            co.addProperty("message", msg);
            IoSession ses = null;
            if (session.getAttribute("customerServiceUser") != null) {
                ses = SessionManager.getInstance().getSession(session, msg.getTo());
            } else {
                ses = SessionManager.getInstance().getCustomerServiceSessionBy(session, msg.getTo());
            }
            ses.write(co.toJson());
        }
    }
}
