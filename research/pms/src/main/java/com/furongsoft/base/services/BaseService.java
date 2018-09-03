package com.furongsoft.base.services;

import com.furongsoft.base.repositories.BaseRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 基础服务
 *
 * @param <T> 实体类型
 * @param <V> 主键类型
 * @author Alex
 */
public class BaseService<T, V> implements BaseRepository<T, V> {
    private BaseRepository repository;

    public BaseService(BaseRepository repository) {
        this.repository = repository;
    }

    private BaseService() {
    }

    public Page<T> findAll(Map<String, Object> params) {
        return null;
    }

    public void deleteBatch(String delete) {
    }

    @Override
    @NonNull
    public Optional<T> findOne(@NonNull Predicate predicate) {
        return repository.findOne(predicate);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull Predicate predicate) {
        return repository.findAll(predicate);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull Predicate predicate, @NonNull Sort sort) {
        return repository.findAll(predicate, sort);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull Predicate predicate, @NonNull OrderSpecifier<?>... orders) {
        return repository.findAll(predicate, orders);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull OrderSpecifier<?>... orders) {
        return repository.findAll(orders);
    }

    @Override
    @NonNull
    public Page<T> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    @NonNull
    public long count(@NonNull Predicate predicate) {
        return repository.count(predicate);
    }

    @Override
    @NonNull
    public boolean exists(@NonNull Predicate predicate) {
        return repository.exists(predicate);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    @NonNull
    public Page<T> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        return (S) repository.save(entity);
    }

    @Override
    @NonNull
    public <S extends T> Iterable<S> saveAll(@NonNull Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    @NonNull
    public Optional<T> findById(@NonNull V v) {
        return repository.findById(v);
    }

    @Override
    public boolean existsById(@NonNull V v) {
        return repository.existsById(v);
    }

    @Override
    @NonNull
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Override
    @NonNull
    public Iterable<T> findAllById(@NonNull Iterable<V> vs) {
        return repository.findAllById(vs);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(@NonNull V v) {
        repository.deleteById(v);
    }

    @Override
    public void delete(@NonNull T entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll(@NonNull Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @NonNull
    public Optional<T> findOne(Specification<T> specification) {
        return repository.findOne(specification);
    }

    @Override
    @NonNull
    public List<T> findAll(Specification<T> specification) {
        return repository.findAll(specification);
    }

    @Override
    @NonNull
    public Page<T> findAll(Specification<T> specification, @NonNull Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    @NonNull
    public List<T> findAll(Specification<T> specification, @NonNull Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public long count(Specification<T> specification) {
        return repository.count(specification);
    }
}
