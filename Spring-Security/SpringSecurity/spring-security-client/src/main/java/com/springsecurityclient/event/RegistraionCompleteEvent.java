package com.springsecurityclient.event;

import com.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

@Getter
@Setter

public class RegistraionCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public RegistraionCompleteEvent(User user,String applicationUrl) {
        super(user);
        this.user= user;
        this.applicationUrl= applicationUrl;
    }
}


