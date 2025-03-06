package com.ElectronicStoreSpringboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Roles")
public class Role {
    @Id
    private String roleId;
    private String roleName;
}
