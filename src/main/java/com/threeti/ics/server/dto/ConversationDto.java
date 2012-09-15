package com.threeti.ics.server.dto;

import com.threeti.ics.server.domain.protocoldefinition.ConversationTopic;
import com.threeti.ics.server.domain.protocoldefinition.message.Message;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public class ConversationDto implements Dto{
    private String serviceToken;
    private ConversationTopic topic;
    private List<Message> messages;

    public ConversationDto() {
    }

    public ConversationDto(String serviceToken, ConversationTopic topic, List<Message> messages) {
        this.serviceToken = serviceToken;
        this.topic = topic;
        this.messages = messages;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public ConversationTopic getTopic() {
        return topic;
    }

    public void setTopic(ConversationTopic topic) {
        this.topic = topic;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
