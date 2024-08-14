package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;

    public Product create(ProductDTO dto) {
        return productRepository.save(Product.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .recipes(recipeRepository.findAllByIdIn(dto.getRecipeIds()))
                .build());
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product readById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found - " + id));
    }

    public Set<Product> readAllByIdIn(List<Long> ids) {
        return productRepository.findAllByIdIn(ids);
    }

    public List<Product> readByRecipeId(Long id) {
        return productRepository.findByRecipesId(id);
    }

    public Product update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new RuntimeException("Product not found - " + product.getId());
        }
        return productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found - " + id);
        }
        productRepository.deleteById(id);
    }
}
