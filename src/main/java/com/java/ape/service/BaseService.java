package com.java.ape.service;

import com.java.ape.entity.BaseEntity;
import io.vavr.control.Option;

/**
 * Created by coarse_horse on 16/07/2020
 */
public interface BaseService<T extends BaseEntity> {

    Option<T> findById(Long id);
    
    T findByIdOrThrow(Long id);
    
    T save(T entity);
}
