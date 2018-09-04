package com.furongsoft.openmes.research.pms.services.user;

import com.furongsoft.base.annotations.RestfulService;
import com.furongsoft.base.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RestfulService(path = "/api/v1")
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService<User, Long> {
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }
}
