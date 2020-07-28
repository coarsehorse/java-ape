package com.java.ape.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by coarse_horse on 18/07/2020
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends FullDatedEntity {

    @Column(columnDefinition = "text", nullable = false)
    private String text;
    @ManyToOne
    private User user;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;
}
