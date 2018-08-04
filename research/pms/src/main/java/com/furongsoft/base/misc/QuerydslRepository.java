package com.furongsoft.base.misc;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.Nonnull;

/**
 * Querydsl数据仓库
 *
 * @param <T>  对象
 * @param <QT> 查询对象
 */
@NoRepositoryBean
public interface QuerydslRepository<T, QT extends EntityPath<?>> extends QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {
    @Override
    default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QT root) {
        JpaUtils.bindQuerydsl(bindings, root);
    }
}
