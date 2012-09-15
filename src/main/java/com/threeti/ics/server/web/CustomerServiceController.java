package com.threeti.ics.server.web;

import com.threeti.ics.server.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CustomerServiceController {
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public
    @ResponseBody
    String login(@WebParam String userName, @WebParam String password) {
        return Boolean.toString(securityService.login(userName, password));
    }
}
