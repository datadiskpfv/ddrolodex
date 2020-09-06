package uk.co.datadisk.ddrolodex.domain.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.co.datadisk.ddrolodex.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends BaseEntity implements UserDetails, CredentialsContainer {

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;


    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Role role;

    private boolean isActive;
    private boolean isNotLocked;

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        // I have two ways to load authorities, fine grain like user.create, user.delete, etc
        // or a plain role ADMIN, USER from the role

        // Fine grain authorities
        Set<GrantedAuthority> authorities = this.role.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());

        // Role name authority
        SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(role.getName());
        authorities.add(roleAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
