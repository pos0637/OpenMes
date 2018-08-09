package com.furongsoft.rbac.repositories;

import com.furongsoft.base.misc.QuerydslRepository;
import com.furongsoft.rbac.entities.QResource;
import com.furongsoft.rbac.entities.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/resource")
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long>, QuerydslRepository<Resource, QResource> {
}
