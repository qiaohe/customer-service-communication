package com.threeti.ics.server.domain.protocoldefinition.commandresponse;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.Conversation;
import com.threeti.ics.server.domain.protocoldefinition.identity.CustomerServiceUser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 13/09/12
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class CustomerServiceLoginResponse {
    private CustomerServiceUser customerServiceUser;
    private List<Conversation> conversations;
    private HashMap<String, Long> queueSummary;

    public CustomerServiceUser getCustomerServiceUser() {
        return customerServiceUser;
    }

    public void setCustomerServiceUser(CustomerServiceUser customerServiceUser) {
        this.customerServiceUser = customerServiceUser;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setQueueSummary(HashMap<String, Long> queueSummary) {
        this.queueSummary = queueSummary;
    }

    public HashMap<String, Long> getQueueSummary() {
        return queueSummary;
    }

    public String toJson() {
        CommonObject co = new CommonObject();
        co.setType("004");
        co.addProperty("customerServiceUser", customerServiceUser);
        co.addProperty("conversations", conversations);
        co.addProperty("queueSummary", getQueueSummary());
        return ObjectJsonMapper.getJsonStringBy(co);
    }
}
