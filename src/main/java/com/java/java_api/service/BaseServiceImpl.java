package com.java.java_api.service;

import com.java.java_api.entity.BaseEntity;
import com.java.java_api.repository.BaseRepository;
import io.vavr.control.Option;
import lombok.Getter;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Getter
public abstract class BaseServiceImpl<T extends BaseEntity, R extends BaseRepository<T>> implements BaseService<T> {

    private final R repository;
    
    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }
    
    @Override
    public Option<T> findById(Long id) {
        return Option.ofOptional(repository.findById(id));
    }
    
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }
}