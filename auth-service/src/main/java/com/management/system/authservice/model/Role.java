package com.management.system.authservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Table(name = "role")
public class Role extends BaseEntity{

    private String name;
}
