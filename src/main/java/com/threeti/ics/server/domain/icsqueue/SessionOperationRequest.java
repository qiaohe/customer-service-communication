package com.threeti.ics.server.domain.icsqueue;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.ConversationKey;
import com.threeti.ics.server.domain.protocoldefinition.SessionOperationType;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class SessionOperationRequest {
    private String customerServiceUserName;
    private List<ConversationKey> conversationKeys = new ArrayList<>();
    private SessionOperationType operationType;
    private Map<String, String> additional = new HashMap<>();

    public SessionOperationRequest() {
    }

    public SessionOperationRequest(String customerService, SessionOperationType operationType) {
        this.customerServiceUserName = customerService;
        this.operationType = operationType;
    }

    public String getCustomerServiceUserName() {
        return customerServiceUserName;
    }

    public void setCustomerServiceUserName(String customerServiceUserName) {
        this.customerServiceUserName = customerServiceUserName;
    }

    public SessionOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(SessionOperationType operationType) {
        this.operationType = operationType;
    }

    public List<ConversationKey> getConversationKeys() {
        return conversationKeys;
    }

    public void setConversationKeys(List<ConversationKey> conversationKeys) {
        this.conversationKeys = conversationKeys;
    }

    public Map<String, String> getAdditional() {
        return additional;
    }

    public void setAdditional(Map<String, String> additional) {
        this.additional = additional;
    }

    @JsonIgnore
    public boolean isFromSessionQueueWithinTerminate() {
        return SessionOperationType.TERMINATE.equals(operationType)
                && "sessionqueue".equals(additional.get("from"));
    }
}
