package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.ProductDTO;
import com.alexpyslar03.productselectorbackend.entity.Product;
import com.alexpyslar03.productselectorbackend.exception.ProductNotFoundException;
import com.alexpyslar03.productselectorbackend.repository.ProductRepository;
import com.alexpyslar03.productselectorbackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Сервисный класс для работы с продуктами.
 * Содержит методы для создания, чтения, обновления и удаления продуктов.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;

    /**
     * Создает новый продукт на основе предоставленного DTO и сохраняет его в репозитории.
     *
     * @param dto DTO с данными нового продукта.
     * @return Созданный продукт.
     */
    public Product create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .recipes(recipeRepository.findAllByIdIn(dto.getRecipeIds())) // Установка связанных рецептов
                .build();
        Product savedProduct = productRepository.save(product);
        logger.info("Продукт с ID {} успешно создан.", savedProduct.getId());
        return savedProduct;
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return Список всех продуктов.
     */
    public List<Product> readAll() {
        List<Product> products = productRepository.findAll();
        logger.info("Запрошен список всех продуктов.");
        return products;
    }

    /**
     * Возвращает продукт по его идентификатору.
     * Если продукт не найден, выбрасывается исключение ProductNotFoundException.
     *
     * @param id Идентификатор продукта.
     * @return Продукт с указанным идентификатором.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    public Product readById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Продукт с идентификатором %d не найден.", id)));
        logger.info("Продукт с ID {} найден.", id);
        return product;
    }

    /**
     * Возвращает набор продуктов по предоставленным идентификаторам.
     * Если продукты не найдены, выбрасывается исключение ProductNotFoundException.
     *
     * @param ids Список идентификаторов продуктов.
     * @return Набор продуктов с указанными идентификаторами.
     * @throws ProductNotFoundException Если продукты с указанными идентификаторами не найдены.
     */
    public Set<Product> readAllByIdIn(List<Long> ids) {
        Set<Product> products = productRepository.findAllByIdIn(ids);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Не найдено продуктов с указанными идентификаторами.");
        }
        logger.info("Найдено {} продуктов по указанным ID.", products.size());
        return products;
    }

    /**
     * Возвращает список продуктов по идентификатору рецепта.
     * Если продукты не найдены, выбрасывается исключение ProductNotFoundException.
     *
     * @param id Идентификатор рецепта.
     * @return Список продуктов, связанных с указанным рецептом.
     * @throws ProductNotFoundException Если продукты для указанного рецепта не найдены.
     */
    public List<Product> readByRecipeId(Long id) {
        List<Product> products = productRepository.findByRecipesId(id);
        if (products.isEmpty()) {
            throw new ProductNotFoundException(String.format("Продукты для рецепта с идентификатором %d не найдены.", id));
        }
        logger.info("Найдено {} продуктов для рецепта с ID {}.", products.size(), id);
        return products;
    }

    /**
     * Возвращает список продуктов по списку идентификаторов рецептов.
     * Если продукты не найдены, выбрасывается исключение ProductNotFoundException.
     *
     * @param ids Список идентификаторов рецептов.
     * @return Список продуктов, связанных с указанными рецептами.
     * @throws ProductNotFoundException Если продукты для указанных рецептов не найдены.
     */
    public List<Product> readByRecipeIdIn(List<Long> ids) {
        List<Product> products = productRepository.findByRecipesIdIn(ids);
        if (products.isEmpty()) {
            throw new ProductNotFoundException(String.format("Продукты для рецептов с идентификаторами %s не найдены.", ids));
        }
        logger.info("Найдено {} продуктов для рецептов с ID {}.", products.size(), ids);
        return products;
    }

    /**
     * Обновляет существующий продукт.
     * Если продукт с указанным идентификатором не найден, выбрасывается исключение ProductNotFoundException.
     *
     * @param product Продукт с обновленными данными.
     * @return Обновленный продукт.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    public Product update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException(String.format("Невозможно обновить. Продукт с идентификатором %d не найден.", product.getId()));
        }
        Product updatedProduct = productRepository.save(product);
        logger.info("Продукт с ID {} успешно обновлен.", product.getId());
        return updatedProduct;
    }

    /**
     * Удаляет продукт по его идентификатору.
     * Если продукт с указанным идентификатором не найден, выбрасывается исключение ProductNotFoundException.
     *
     * @param id Идентификатор продукта для удаления.
     * @throws ProductNotFoundException Если продукт с указанным идентификатором не найден.
     */
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(String.format("Невозможно удалить. Продукт с идентификатором %d не найден.", id));
        }
        productRepository.deleteById(id);
        logger.info("Продукт с ID {} успешно удален.", id);
    }
}