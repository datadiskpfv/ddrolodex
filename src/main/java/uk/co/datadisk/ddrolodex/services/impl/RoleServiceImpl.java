package uk.co.datadisk.ddrolodex.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.datadisk.ddrolodex.domain.security.Role;
import uk.co.datadisk.ddrolodex.repositories.security.RoleRepository;
import uk.co.datadisk.ddrolodex.services.RoleService;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name).orElseThrow(() -> new RuntimeException("NO_ROLE_FOUND"));
    }
}
