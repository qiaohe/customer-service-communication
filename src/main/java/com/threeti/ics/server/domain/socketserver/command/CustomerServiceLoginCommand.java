package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.CustomerServiceUserDao;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.domain.icsqueue.PublicQueue;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.commandresponse.CustomerServiceLoginResponse;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class CustomerServiceLoginCommand extends AbstractCommand implements Command {
    @Autowired
    private CustomerServiceUserDao customerServiceUserDao;
    @Autowired
    private ConversationDao conversationDao;


    public CustomerServiceLoginCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        CustomerServiceUser cu = ObjectJsonMapper.getObjectBy(getRequestAsString(), CustomerServiceUser.class);
        CustomerServiceUser user = customerServiceUserDao.get(cu.getUserName());
        CustomerServiceLoginResponse rep = new CustomerServiceLoginResponse();
        rep.setCustomerServiceUser(user);
        rep.setQueueSummary(conversationDao.getTotalSummary(user.getUserName()));
        session.setAttribute("customerServiceUser", user);
        rep.setConversations(conversationDao.get());
        session.write(rep.toJson());
    }
}

