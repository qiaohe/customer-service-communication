package com.threeti.ics.server.domain.socketserver.handler;

import com.threeti.ics.server.domain.icsqueue.SessionOperationRequest;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public interface SessionHandler {
    public void handle(SessionOperationRequest request, IoSession session);
}
