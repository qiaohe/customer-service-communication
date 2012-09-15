package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.conversation.ConversationDao;
import com.threeti.ics.server.dao.conversation.ConversationTopicDao;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.ConversationTopic;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.BuildConversationRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class BuildConversationCommand extends AbstractCommand implements Command {
    @Autowired
    private ConversationTopicDao conversationTopicDao;
    @Autowired
    private ConversationDao conversationDao;

    public BuildConversationCommand(Object request) {
        super(request);
    }


    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public void execute(IoSession session) {
        BuildConversationRequest bcr = ObjectJsonMapper.getObjectBy(getRequestAsString(), BuildConversationRequest.class);
        Conversation c = bcr.getConversation();
        ConversationTopic topic = conversationTopicDao.add(c.getTopic());
        c.setTopic(topic);
        conversationDao.add(c);
        session.setAttribute("user", bcr.getServiceToken());
        CommonObject o = new CommonObject();
        o.setType("002");
        o.addProperty("data", c);
//        PublicQueue.getInstance().add(c);
        session.write(o.toJson());
    }
}
