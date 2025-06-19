package me.danhthanhf.distant_class.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity{

    private String email;

    // to avoid issues with password storage, use a secure hashing algorithm
    @Column(columnDefinition = "TEXT")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
            (name = "user_roles", joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id"),
                    @JoinColumn(name = "role_id", referencedColumnName = "id")
            })
    private List<Role> roles;
}
