package com.meumural.projetobackend.dto.roles;

import javax.management.relation.Role;
import java.util.List;

public record RecoveryUserDTO(
        Long id,
        String email,
        List<Role> roles
) {
}
