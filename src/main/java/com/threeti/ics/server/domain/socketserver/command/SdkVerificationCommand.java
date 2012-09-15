package com.threeti.ics.server.domain.socketserver.command;

import com.threeti.ics.server.common.ObjectJsonMapper;
import com.threeti.ics.server.dao.MobileDeviceDao;
import com.threeti.ics.server.dao.ServiceTokenDao;
import com.threeti.ics.server.domain.mobile.ServiceToken;
import com.threeti.ics.server.domain.protocoldefinition.CommonObject;
import com.threeti.ics.server.domain.protocoldefinition.commandrequest.SdkVerificationRequest;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class SdkVerificationCommand extends AbstractCommand implements Command {
    @Autowired
    private MobileDeviceDao mobileDeviceDao;
    @Autowired
    private ServiceTokenDao serviceTokenDao;


    public SdkVerificationCommand() {
        super();
    }

    public SdkVerificationCommand(Object request) {
        super(request);
    }

    @Override
    public void execute(IoSession session) {
        System.out.println(mobileDeviceDao == null);
        SdkVerificationRequest sr = ObjectJsonMapper.getObjectBy(getRequestAsString(), SdkVerificationRequest.class);
        mobileDeviceDao.add(sr.getMobileDevice());
        ServiceToken token = serviceTokenDao.add(sr.getServiceToken());
        CommonObject o = new CommonObject();
        o.setType("001");
        o.addProperty("serviceToken", token.getToken());
        session.write(o.toJson());
    }
}
