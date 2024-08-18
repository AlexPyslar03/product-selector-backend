package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.exception.ProductNotFoundException;
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
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Set<Product> readAllByIdIn(List<Long> ids) {
        Set<Product> products = productRepository.findAllByIdIn(ids);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found for the provided IDs.");
        }
        return products;
    }

    public List<Product> readByRecipeId(Long id) {
        List<Product> products = productRepository.findByRecipesId(id);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found for the provided recipe ID - " + id);
        }
        return products;
    }

    public Product update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException(product.getId());
        }
        return productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}