package me.danhthanhf.distant_class.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import me.danhthanhf.distant_class.common.enums.RoleEnum;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity{

    @Column(name = "name", nullable = false, unique = true)
    private RoleEnum name;
}
