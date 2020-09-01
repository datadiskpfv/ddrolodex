package uk.co.datadisk.ddrolodex.domain.security;

import lombok.*;
import uk.co.datadisk.ddrolodex.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority extends BaseEntity {

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    public Set<Role> roles;
}
