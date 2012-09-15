package com.threeti.ics.server.domain.protocoldefinition;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 07/09/12
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class ConversationKey {
    private String visitor;
    private String productId;

    public ConversationKey() {
    }

    public ConversationKey(String visitor, String productId) {
        this.visitor = visitor;
        this.productId = productId;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
