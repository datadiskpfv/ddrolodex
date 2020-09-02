package uk.co.datadisk.ddrolodex.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.repositories.security.AuthorityRepository;
import uk.co.datadisk.ddrolodex.services.AuthorityService;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority createAuthority(String permission) {
        Authority authority = new Authority(permission);
        return authorityRepository.save(authority);
    }

    @Override
    public Authority findAuthorityByName(String permission) {
        return authorityRepository.findAuthorityByPermission(permission).orElseThrow(() -> new RuntimeException("NO_AUTHORITY_FOUND"));
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority update(Authority authority) {
        authorityRepository.save(authority);
        return authority;
    }

    @Override
    public void delete(Long id) {
        authorityRepository.deleteById(id);
    }
}
