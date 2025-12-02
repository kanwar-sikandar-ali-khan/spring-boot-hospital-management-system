package com.codingshuttle.youtube.hospitalManagement.entity;

import com.codingshuttle.youtube.hospitalManagement.entity.type.AuthProviderType;
import com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType;
import com.codingshuttle.youtube.hospitalManagement.security.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user", indexes = {
        @Index(name = "idx_provider_id_provider_type", columnList = "providerId, providerType")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true, nullable = false)
    private String username;
    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING) ///for  enums
    private AuthProviderType providerType;

    //"@ElementCollection creates a separate table similar to a join table, but it’s not a ManyToMany — it’s a one-to-many mapping to simple values, owned by one entity only."
    @ElementCollection(fetch = FetchType.EAGER) // will make another table app_user_roles  this  is not N_N relation it only store values
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles = new HashSet<>();

    //// this only works on login not at time  of signup
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }
}


















