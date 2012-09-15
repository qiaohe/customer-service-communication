package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.icsqueue.PublicQueue;
import com.threeti.ics.server.domain.icsqueue.QueueRequest;
import com.threeti.ics.server.domain.icsqueue.QueueType;
import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveQueueCommand extends AbstractCommand implements Command {
    public RetrieveQueueCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        QueueRequest qr = ObjectJsonMapper.getObjectBy(getRequestAsString(), QueueRequest.class);
        if (qr.getType().equals(QueueType.PUBLIC)) {
            session.write(ObjectJsonMapper.getJsonStringBy(PublicQueue.getInstance().getConversations(qr)));
        }
    }
}
