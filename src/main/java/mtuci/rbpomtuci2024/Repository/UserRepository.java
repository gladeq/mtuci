package mtuci.rbpomtuci2024.Repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mtuci.rbpomtuci2024.model.ApplicationUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {
    static User save(User user) {
        return null;
    }

    Optional<ApplicationUser> findByEmail(String email);
}

