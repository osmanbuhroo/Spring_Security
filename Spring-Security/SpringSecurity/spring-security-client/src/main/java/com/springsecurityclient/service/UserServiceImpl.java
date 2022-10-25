package com.springsecurityclient.service;


import com.springsecurityclient.entity.User;
import com.springsecurityclient.entity.VerificationToken;
import com.springsecurityclient.model.UserModel;
import com.springsecurityclient.repository.UserRepository;
import com.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;


    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setUsername(userModel.getUserName());
        user.setRole(userModel.getRole());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override

    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken=
                verificationTokenRepository.findByToken(token);
        if(verificationToken== null){
            return "invalid";

        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationtime().getTime()
                - cal.getTime().getTime()<=0 ){
            verificationTokenRepository.delete(verificationToken);
            return "expired";

        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";


    }
}
