package com.threeti.ics.server.domain.socketserver.command;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public abstract class AbstractCommand implements Command {
    private Object request;

    public AbstractCommand() {
    }

    public AbstractCommand(Object request) {
        this.request = request;
    }

    @Override
    public String getCommandName() {
        return this.getClass().getName();
    }

    public Object getRequest() {
        return request;
    }

    public String getRequestAsString() {
        return request != null ? request.toString() : null;
    }

    @Override
    public void execute(IoSession session) {

    }
}
