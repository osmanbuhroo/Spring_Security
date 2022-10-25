package com.springsecurityclient.event.listener;

import com.springsecurityclient.entity.User;
import com.springsecurityclient.event.RegistraionCompleteEvent;
import com.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistraionCompleteEvent> {

    @Autowired
    private UserService userService;

    public void onApplicationEvent(RegistraionCompleteEvent event) {

       //send mail to user
        User user = event.getUser();
        String  token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        userService.saveVerificationTokenForUser(token,user);
        String url =event.getApplicationUrl()+"/verifyRegistration?token="+token;

        // send verfication on email
        log.info("click the link to verfiy your account {}",url);
    }



    }

