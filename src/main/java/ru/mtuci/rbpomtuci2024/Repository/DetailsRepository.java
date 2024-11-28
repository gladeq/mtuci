package ru.mtuci.rbpomtuci2024.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.rbpomtuci2024.model.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Long> {

}

