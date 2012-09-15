package com.threeti.ics.server.dao.conversation;

import com.threeti.ics.server.domain.protocoldefinition.ConversationTopic;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 11/09/12
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public interface ConversationTopicDao {
    public ConversationTopic add(ConversationTopic topic);

    public ConversationTopic get(final String productId);

    public boolean hasProductId(final String productId);

    public ConversationTopic save(ConversationTopic topic);
}
