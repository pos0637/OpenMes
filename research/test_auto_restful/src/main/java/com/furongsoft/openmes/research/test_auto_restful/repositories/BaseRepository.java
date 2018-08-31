package com.furongsoft.openmes.research.test_auto_restful.repositories;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T, V> extends PagingAndSortingRepository<T, V>, QuerydslPredicateExecutor<T> {
}
