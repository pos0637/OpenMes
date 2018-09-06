package com.furongsoft.base.services;

import com.furongsoft.base.misc.JpaUtils;
import com.furongsoft.base.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    protected BaseRepository<T, V> repository;

    public BaseService(BaseRepository<T, V> repository) {
        this.repository = repository;
    }

    private BaseService() {
    }

    /**
     * 查找所有数据
     *
     * @param params 参数列表
     * @param clazz  实体类型
     * @return 所有数据
     */
    public Page<T> findAll(Map<String, Object> params, Class<T> clazz) {
        Pageable pageable;
        if (params.containsKey("pageNum") && params.containsKey("pageSize")) {
            pageable = PageRequest.of((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE);
        }

        return findAll(JpaUtils.generateSpecification(params, clazz), pageable);
    }

    public void deleteInBatch(String delete) {
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
