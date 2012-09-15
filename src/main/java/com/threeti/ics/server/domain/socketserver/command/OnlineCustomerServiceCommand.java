package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.socketserver.server.SessionManager;
import org.apache.mina.core.session.IoSession;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 14/09/12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class OnlineCustomerServiceCommand extends AbstractCommand implements Command {
    public OnlineCustomerServiceCommand(Object request) {
        super(request);
    }


    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public void execute(IoSession session) {
        List<CustomerServiceUser> us = SessionManager.getInstance().getCustomerServiceUsers(session);
        CommonObject co = new CommonObject();
        co.setType("006");
        co.addProperty("customerServiceUsers", us);
        session.write(co.toJson());
    }

}
