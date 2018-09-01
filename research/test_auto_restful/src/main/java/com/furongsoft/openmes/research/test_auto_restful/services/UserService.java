package com.furongsoft.openmes.research.test_auto_restful.services;

import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulService;
import com.furongsoft.openmes.research.test_auto_restful.entities.User;
import com.furongsoft.openmes.research.test_auto_restful.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RestfulService(basePath = "/api/v1")
@Service
public class UserService extends BaseService<User, Long> {
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }
}
