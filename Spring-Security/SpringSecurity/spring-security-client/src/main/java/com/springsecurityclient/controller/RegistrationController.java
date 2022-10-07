package com.springsecurityclient.controller;

import com.springsecurityclient.entity.User;
import com.springsecurityclient.event.RegistraionCompleteEvent;
import com.springsecurityclient.model.UserModel;
import com.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public  String registerUser(@RequestBody UserModel userModel , final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistraionCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "sucess";
    }
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result= userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")){
            return ".......User verifies Sucessfully......";
        }
        return "Bad User";
    }

     private String applicationUrl(HttpServletRequest request) {
        return  "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }


}
