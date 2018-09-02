package com.furongsoft.openmes.research.pms;

import com.furongsoft.base.misc.JpaUtils;
import com.furongsoft.rbac.entities.QResource;
import com.furongsoft.rbac.entities.Resource;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.annotation.Nonnull;

@RepositoryRestResource(path = "resource")
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long>, QuerydslPredicateExecutor<Resource>, QuerydslBinderCustomizer<QResource> {
    @Override
    default void customize(@Nonnull QuerydslBindings querydslBindings, @Nonnull QResource qResource) {
        JpaUtils.bindQuerydsl(querydslBindings, qResource);
    }
}
