package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import com.threeti.ics.server.domain.socketserver.handler.HandlerFactory;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class SessionOperationCommand extends AbstractCommand implements Command {
    public SessionOperationCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        SessionOperationRequest request = ObjectJsonMapper.getObjectBy(getRequestAsString(), SessionOperationRequest.class);
        HandlerFactory.getInstance().createHandler(request.getOperationType()).handle(request, session);
    }
}
