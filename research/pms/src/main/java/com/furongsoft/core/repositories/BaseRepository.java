package com.furongsoft.core.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础数据仓库
 *
 * @param <T> 实体类型
 * @param <V> 主键类型
 * @author Alex
 */
@NoRepositoryBean
public interface BaseRepository<T, V> extends PagingAndSortingRepository<T, V>, JpaSpecificationExecutor<T> {
}
