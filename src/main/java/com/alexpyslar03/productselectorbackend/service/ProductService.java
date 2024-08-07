package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product create(ProductDTO dto) {
        return productRepository.save(Product.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .build());
    }
    public List<Product> readAll() {
        return productRepository.findAll();
    }
    public Product update(Product product) {
        return productRepository.save(product);
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
