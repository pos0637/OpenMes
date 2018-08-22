package com.furongsoft.openmes.research.pms.task;

import com.furongsoft.base.misc.QuerydslRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "/task")
@Transactional(rollbackFor = Exception.class)
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, QuerydslRepository<Task, QTask> {
}
