package com.furongsoft.openmes.research.pms.services.task;

import com.furongsoft.base.annotations.RestfulService;
import com.furongsoft.base.services.BaseService;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroup;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupRepository;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupUser;
import com.furongsoft.openmes.research.pms.services.userGroup.UserGroupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public TaskService(
            @Qualifier("com.furongsoft.openmes.research.pms.services.task.TaskRepository") TaskRepository repository,
            @Qualifier("com.furongsoft.openmes.research.pms.services.userGroup.UserGroupRepository") UserGroupRepository userGroupRepository,
            @Qualifier("com.furongsoft.openmes.research.pms.services.userGroup.UserGroupUserRepository") UserGroupUserRepository userGroupUserRepository) {
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

    /**
     * 创建用户组
     *
     * @param users 用户索引列表
     */
    private void saveUserGroup(List<Long> users) {
        if ((users == null) || (users.size() == 0)) {
            return;
        }

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
