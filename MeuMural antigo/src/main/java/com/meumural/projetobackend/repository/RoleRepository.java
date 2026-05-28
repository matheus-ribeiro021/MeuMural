package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.Role;
import com.meumural.projetobackend.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository< Role, Integer> {
    Role findByName(RoleName role);
}
