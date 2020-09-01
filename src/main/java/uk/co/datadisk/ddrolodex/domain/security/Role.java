package uk.co.datadisk.ddrolodex.domain.security;

import lombok.*;
import uk.co.datadisk.ddrolodex.domain.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role extends BaseEntity {

    private String name;

    //@ManyToMany(mappedBy = "roles")
    //private Set<User> users;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
            joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;
}
