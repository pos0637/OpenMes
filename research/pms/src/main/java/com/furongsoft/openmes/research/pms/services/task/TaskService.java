package com.furongsoft.openmes.research.pms.services.task;

import com.furongsoft.base.annotations.RestfulService;
import com.furongsoft.base.services.BaseService;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroup;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupRepository;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupUser;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestfulService(path = "/api/v1")
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskService extends BaseService<Task, Long> {
    private final UserGroupRepository userGroupRepository;
    private final UserGroupUserRepository userGroupUserRepository;

    @Autowired
    public TaskService(TaskRepository repository, UserGroupRepository userGroupRepository, UserGroupUserRepository userGroupUserRepository) {
        super(repository);
        this.userGroupRepository = userGroupRepository;
        this.userGroupUserRepository = userGroupUserRepository;
    }

    @Override
    public <S extends Task> S save(S entity) {
        saveUserGroup(entity.getOwnersIdList());
        saveUserGroup(entity.getAccepterIdList());

        return super.save(entity);
    }

    private void saveUserGroup(List<Long> users) {
        UserGroup userGroup = new UserGroup();
        userGroupRepository.save(userGroup);

        users.forEach(id -> {
            UserGroupUser userGroupUser = new UserGroupUser();
            userGroupUser.setUserGroupId(userGroup.getId());
            userGroupUser.setUserId(id);
            userGroupUserRepository.save(userGroupUser);
        });
    }
}
