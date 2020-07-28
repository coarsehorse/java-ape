package com.java.ape.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Created by coarse_horse on 15/07/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends FullDatedEntity {

    @Column(unique = true, nullable = false)
    private String nickname;
    private String fullName;
    @Column(nullable = false)
    private String password;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<String> authorities;
    private Boolean enabled;
}
