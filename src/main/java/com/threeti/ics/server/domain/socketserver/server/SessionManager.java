package com.threeti.ics.server.domain.socketserver.server;

import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;
import com.threeti.ics.server.domain.protocoldefinition.identity.Visitor;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public List<CustomerServiceUser> getCustomerServiceUsers(IoSession ses) {
        List<CustomerServiceUser> result = new ArrayList<>();
        for (IoSession session : ses.getService().getManagedSessions().values()) {
            Object u = session.getAttribute("customerServiceUser");
            if (u != null && u instanceof CustomerServiceUser) {
                result.add((CustomerServiceUser) u);
            }
        }
        return result;
    }

    public List<Visitor> getVisitors(IoSession ses) {
        List<Visitor> result = new ArrayList<>();
        for (IoSession session : ses.getService().getManagedSessions().values()) {
            Object u = session.getAttribute("user");
            if (u != null && u instanceof Visitor) {
                result.add((Visitor) u);
            }
        }
        return result;
    }

    public List<IoSession> getCustomerServiceSessions(IoSession session) {
        List<IoSession> result = new ArrayList<>();
        for (IoSession s : session.getService().getManagedSessions().values()) {
            Object u = s.getAttribute("customerServiceUser");
            if (u != null && u instanceof CustomerServiceUser) {
                result.add(s);
            }
        }
        return result;
    }

    public IoSession getSession(IoSession session, String serviceToken) {
        for (IoSession s : session.getService().getManagedSessions().values()) {
            Object u = s.getAttribute("customerServiceUser");
            if (u != null && u instanceof CustomerServiceUser) {
                if (serviceToken.equals(((CustomerServiceUser) u).getServiceToken())) {
                    return s;
                }
            }
            Object u1 = s.getAttribute("user");
            if (u1 != null && serviceToken.equals(u1.toString())) {
                return s;
            }
        }
        return null;
    }

    public IoSession getCustomerServiceSessionBy(IoSession session, String customerServiceUserName) {
        for (IoSession s : session.getService().getManagedSessions().values()) {
            Object u = s.getAttribute("customerServiceUser");
            if (u != null && u instanceof CustomerServiceUser) {
                if (customerServiceUserName.equals(((CustomerServiceUser) u).getUserName())) {
                    return s;
                }
            }
        }
        return null;
    }


}
