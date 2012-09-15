package com.threeti.ics.server.domain.socketserver.command;

import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public interface Command {
    public String getCommandName();

    public Object getRequest();

    public void execute(IoSession session);
}
