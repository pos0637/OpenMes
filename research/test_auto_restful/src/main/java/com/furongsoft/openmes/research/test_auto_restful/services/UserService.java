package com.furongsoft.openmes.research.test_auto_restful.services;

import com.furongsoft.openmes.research.test_auto_restful.annotations.RestfulService;
import com.furongsoft.openmes.research.test_auto_restful.entities.User;
import com.furongsoft.openmes.research.test_auto_restful.repositories.UserRepository;

@RestfulService(vo = User.class, repository = UserRepository.class)
public class UserService extends BaseService<User, Long> {
}
