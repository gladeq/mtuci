package Repository;

import model.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {}
