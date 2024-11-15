package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {

}
