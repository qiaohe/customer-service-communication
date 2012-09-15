package com.threeti.ics.server.domain.protocoldefinition.parser;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public enum ProtocolTypeEnum {
    SDKVERIFICATION("001", "sdk verification"),
    BUILDCONVERSATION("002", "build conversation"),
    MESSAGETRANSFER("003", "message Transfer"),
    CUSTOMERSERVICELOGIN("004", "customer service user login"),
    SESSIONOPERATION("005", "Session operation"),
    ONLINECUSTOMERSERVICEUSERLIST("006", "online customer service user list");


    private final String code;
    private final String name;

    ProtocolTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ProtocolTypeEnum getFrom(final String code) {
        for (ProtocolTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
