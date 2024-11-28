package ru.mtuci.rbpomtuci2024.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtuci.rbpomtuci2024.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
