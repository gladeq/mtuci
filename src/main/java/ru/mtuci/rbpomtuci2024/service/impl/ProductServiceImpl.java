package ru.mtuci.rbpomtuci2024.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpomtuci2024.Repository.LicenseRepository;
import ru.mtuci.rbpomtuci2024.Repository.ProductRepository;
import ru.mtuci.rbpomtuci2024.model.License;
import ru.mtuci.rbpomtuci2024.model.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final LicenseRepository licenseRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт по id не найден: " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setBlocked(updatedProduct.isBlocked());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        List<License> licenses = licenseRepository.findByProductId(id);
        if (!licenses.isEmpty()) {
            licenseRepository.deleteAll(licenses);
        }
        productRepository.deleteById(id);
    }
}
