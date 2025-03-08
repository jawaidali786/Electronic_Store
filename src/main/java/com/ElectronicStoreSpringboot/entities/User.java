package com.ElectronicStoreSpringboot.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME", length = 30)
    private String name;
    @Column(name = "USER_PASSWORD", length = 500)
    private String password;
    @Column(name = "USER_EMAIL", unique = true)
    private String email;
    @Column(name = "ABOUT", length = 500)
    private String about;
    @Column(name = "GENDER", length = 6)
    private String Gender;
    //@Column(name = "User_IMAGENAME")
    //private String image_name;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orderlist = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();


    //methods for implementing security by interface UserDetails and its implemented methods.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
