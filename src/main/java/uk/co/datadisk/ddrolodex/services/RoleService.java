package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.Role;

import java.util.List;

public interface RoleService {

    Role create(Role role);

    Role findRoleByName(String name);

    Role update(Role role);

    void deleteById(Long id);
}
