package com.furongsoft.openmes.research.pms;

import com.furongsoft.base.misc.JpaUtils;
import com.furongsoft.rbac.entities.QUser;
import com.furongsoft.rbac.entities.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.annotation.Nonnull;

@RepositoryRestResource(path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
    @Override
    default void customize(@Nonnull QuerydslBindings querydslBindings, @Nonnull QUser qUser) {
        JpaUtils.bindQuerydsl(querydslBindings, qUser);
    }
}
