package com.furongsoft.openmes.research.pms.services.user;

import com.furongsoft.core.annotations.RestfulService;
import com.furongsoft.core.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RestfulService(path = "/api/v1")
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserService extends BaseService<User, Long> {
    @Autowired
    public UserService(@Qualifier("com.furongsoft.openmes.research.pms.services.user.UserRepository") UserRepository repository) {
        super(repository);
    }
}
