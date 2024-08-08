package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RecipeService recipeService;
    public Product create(ProductDTO dto) {
        return productRepository.save(Product.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .recipes(recipeService.readByIds(dto.getRecipeIds()))
                .build());
    }
    public List<Product> readAll() {
        return productRepository.findAll();
    }
    public Product readById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found - " + id));
    }
    public List<Product> readByIds(List<Long> ids) {
        return productRepository.findByIds(ids);
    }
    public List<Product> readByRecipeId(Long id) {
        return productRepository.findByRecipeId(id);
    }
    public Product update(Product product) {
        return productRepository.save(product);
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
