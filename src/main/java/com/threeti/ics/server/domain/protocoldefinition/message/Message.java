package com.threeti.ics.server.domain.protocoldefinition.message;

import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 06/09/12
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private Long id;
    private Conversation conversation;
    private String thread;
    private String topic;
    private HashMap<String, String> header = new HashMap<>();
    private String from;
    private String to;
    private Date date;
    private String messageBody;
    private String replyTo;
    private MessageStatus status;
    private String version;

    public Message() {
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addHeader(final String key, final String value) {
        header.put(key, value);
    }

    public void removeHeader(final String key, final String value) {
        header.remove(key);
    }

    public void UpdateHeader(final String key, final String value) {
        if (header.containsKey(key)) {
            header.put(key, value);
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @JsonIgnore
    public boolean isPublicQueueMessage() {
        return StringUtils.isEmpty(this.to);
    }
}
