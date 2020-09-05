package uk.co.datadisk.ddrolodex.data;

import org.junit.jupiter.params.provider.Arguments;
import uk.co.datadisk.ddrolodex.domain.security.Authority;
import uk.co.datadisk.ddrolodex.domain.security.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ServiceData {

    static Authority authority1 = Authority.builder().permission("user.create").build();
    static Authority authority2 = Authority.builder().permission("user.delete").build();

    static List<Authority> adminAuth = new ArrayList<>(Arrays.asList(authority1, authority2));
    static List<Authority> managerAuth = new ArrayList<>(Arrays.asList(authority1));

    static Role adminRole = Role.builder().name("ADMIN").authorities(adminAuth).build();
    static Role managerRole = Role.builder().name("MANAGER").authorities(managerAuth).build();

    public static Stream<Arguments> getStreamFindUsers() {
        return Stream.of(Arguments.of("ADMIN", adminRole, adminAuth),
                Arguments.of("MANAGER", managerRole, managerAuth));
    }

}
