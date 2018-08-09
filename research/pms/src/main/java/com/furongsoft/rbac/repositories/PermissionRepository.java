package com.furongsoft.rbac.repositories;

import com.furongsoft.base.misc.QuerydslRepository;
import com.furongsoft.rbac.entities.Permission;
import com.furongsoft.rbac.entities.QPermission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "/permission")
@Transactional(rollbackFor = Exception.class)
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long>, QuerydslRepository<Permission, QPermission> {
}
