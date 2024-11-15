package mtuci.rbpomtuci2024.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mtuci.rbpomtuci2024.model.ApplicationRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationRole, Long> {
    static Optional<ApplicationRole> findByName(String name) {
        return null;
    }
}

