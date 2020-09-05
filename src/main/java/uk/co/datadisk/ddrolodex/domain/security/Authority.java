package uk.co.datadisk.ddrolodex.domain.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import uk.co.datadisk.ddrolodex.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority extends BaseEntity {

    @NonNull
    private String permission;

    @Singular
    @JsonBackReference
    @ManyToMany(mappedBy = "authorities")
    public Set<Role> roles;
}
