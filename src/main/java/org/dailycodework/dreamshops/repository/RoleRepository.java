package org.dailycodework.dreamshops.repository;

import org.dailycodework.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Long>{

    Optional<Role> findByName(String roleUser);
}
