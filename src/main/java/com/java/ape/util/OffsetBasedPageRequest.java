package com.java.ape.util;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by coarse_horse on 20/07/2020
 */
@EqualsAndHashCode
@ToString
public class OffsetBasedPageRequest implements Pageable, Serializable {
    
    private static final Long serialVersionUID = -9223372036854775808L;
    
    private Integer limit;
    private Long offset;
    private final Sort sort;
    
    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset, must not be negative.
     * @param limit the number of the elements to be returned, must be greater than 0.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    protected OffsetBasedPageRequest(Long offset, Integer limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        Assert.notNull(sort, "Sort must not be null!");
        
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }
    
    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     */
    public static OffsetBasedPageRequest of(Long offset, Integer limit) {
        return of(offset, limit, Sort.unsorted());
    }
    
    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     */
    public static OffsetBasedPageRequest of(Long offset, Integer limit, Sort sort) {
        return new OffsetBasedPageRequest(offset, limit, sort);
    }
    
    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort direction and properties applied.
     */
    public static OffsetBasedPageRequest of(
        Long offset,
        Integer limit,
        Sort.Direction direction,
        String... properties
    ) {
        return of(offset, limit, Sort.by(direction, properties));
    }
    
    @Override
    public int getPageNumber() {
        return offset.intValue() / limit;
    }
    
    @Override
    public int getPageSize() {
        return limit;
    }
    
    @Override
    public long getOffset() {
        return offset;
    }
    
    @Override
    public Sort getSort() {
        return sort;
    }
    
    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }
    
    public OffsetBasedPageRequest previous() {
        return hasPrevious()
            ? new OffsetBasedPageRequest(getOffset() - getPageSize(), getPageSize(), getSort())
            : this;
    }
    
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }
    
    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0L, getPageSize(), getSort());
    }
    
    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
