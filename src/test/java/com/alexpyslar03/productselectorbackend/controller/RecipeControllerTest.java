package com.alexpyslar03.productselectorbackend.controller;

import com.alexpyslar03.productselectorbackend.dto.RecipeDTO;
import com.alexpyslar03.productselectorbackend.entity.Recipe;
import com.alexpyslar03.productselectorbackend.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRecipe() throws Exception {
        RecipeDTO dto = new RecipeDTO(); // Создайте экземпляр с нужными данными
        Recipe recipe = new Recipe(); // Создайте экземпляр с нужными данными

        when(recipeService.create(any(RecipeDTO.class))).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(recipe.getId())); // Подставьте правильное поле
    }

    @Test
    public void testReadAllRecipes() throws Exception {
        Recipe recipe1 = new Recipe(); // Создайте экземпляр с нужными данными
        Recipe recipe2 = new Recipe(); // Создайте экземпляр с нужными данными
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        when(recipeService.readAll()).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(recipe1.getId())) // Подставьте правильные поля
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(recipe2.getId())); // Подставьте правильные поля
    }

    @Test
    public void testReadRecipeById() throws Exception {
        Recipe recipe = new Recipe(); // Создайте экземпляр с нужными данными

        when(recipeService.readById(anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(recipe.getId())); // Подставьте правильное поле
    }

    @Test
    public void testReadRecipesByIds() throws Exception {
        Recipe recipe1 = new Recipe(); // Создайте экземпляр с нужными данными
        Recipe recipe2 = new Recipe(); // Создайте экземпляр с нужными данными
        Set<Recipe> recipes = Set.of(recipe1, recipe2);

        when(recipeService.readAllByIdIn(anyList())).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/batch")
                        .param("ids", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(recipe1.getId())) // Подставьте правильные поля
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(recipe2.getId())); // Подставьте правильные поля
    }

    @Test
    public void testReadRecipesByProductId() throws Exception {
        Recipe recipe = new Recipe(); // Создайте экземпляр с нужными данными
        List<Recipe> recipes = List.of(recipe);

        when(recipeService.readByProductId(anyLong())).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/product/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(recipe.getId())); // Подставьте правильное поле
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        Recipe recipe = new Recipe(); // Создайте экземпляр с нужными данными

        when(recipeService.update(any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.put("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(recipe.getId())); // Подставьте правильное поле
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}