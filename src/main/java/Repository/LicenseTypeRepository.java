package Repository;

import model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {

}
