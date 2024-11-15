package mtuci.rbpomtuci2024.Repository;

import mtuci.rbpomtuci2024.model.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {

}

