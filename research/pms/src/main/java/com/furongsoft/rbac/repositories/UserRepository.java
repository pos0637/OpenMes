package com.furongsoft.rbac.repositories;

import com.furongsoft.base.misc.QuerydslRepository;
import com.furongsoft.rbac.entities.QUser;
import com.furongsoft.rbac.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "/user")
@Transactional(rollbackFor = Exception.class)
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslRepository<User, QUser> {
}
