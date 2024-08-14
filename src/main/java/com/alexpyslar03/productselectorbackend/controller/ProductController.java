package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        return ResponseEntity.ok(productService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> readById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readById(id));
    }

    @GetMapping("/batch")
    public ResponseEntity<Set<Product>> readByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(productService.readAllByIdIn(ids));
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<List<Product>> readByRecipeId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readByRecipeId(id));
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}