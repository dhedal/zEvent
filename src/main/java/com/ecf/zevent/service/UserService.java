package com.ecf.zevent.service;

import com.ecf.zevent.model.User;
import com.ecf.zevent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserRepository, User> {
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }


}
