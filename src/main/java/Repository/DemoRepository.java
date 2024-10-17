package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.Demo;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Long> {
    Demo findByName(String name);
}
