package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.ApplicationRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<ApplicationRole, Long> {
    static Optional<ApplicationRole> findByName(String name) {
        return null;
    }
}
