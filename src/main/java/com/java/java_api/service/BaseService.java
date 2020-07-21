package com.java.java_api.service;

import com.java.java_api.entity.BaseEntity;
import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by coarse_horse on 16/07/2020
 */
public interface BaseService<T extends BaseEntity> {

    Option<T> findById(Long id);
    
    T save(T entity);
    
    Page<T> findAll(Pageable pageable);
}
