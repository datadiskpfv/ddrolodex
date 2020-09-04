package uk.co.datadisk.ddrolodex.services;

import uk.co.datadisk.ddrolodex.domain.security.Role;

import java.util.List;

public interface RoleService {

    Role findRoleByName(String name);
}
