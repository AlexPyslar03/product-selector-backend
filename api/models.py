from django.db import models
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):   # пользователь
    name = models.CharField(max_length=250) # Имя
    surname = models.CharField(max_length=250) # Фамилия
    email = models.CharField(max_length=250) # Электронная почта
    birth_date = models.DateField(blank=True) # Дата рождения
    password = models.CharField(max_length=250) # Пароль
    registration_date = models.DateField(blank=True) # Дата регистрации
    access_level = models.IntegerField() # Уровень доступа
    pass

class Recipe(models.Model): # Рецепт
    name = models.CharField(max_length=250) # Название
    description = models.CharField(max_length=250) # Описание
    is_vegan = models.BooleanField(default=False) # Веганский
    difficulty_level = models.IntegerField() # Уровень сложности
    rating = models.ManyToManyField(User) # Рейтинг
    icon = models.ImageField(upload_to='product_icons/', null=True, blank=True) # Изображение рецепта

class Product(models.Model): # Продукт
    name = models.CharField(max_length=250) # Название
    recipe = models.ManyToManyField(Recipe) # Рецепт
    icon = models.ImageField(upload_to='product_icons/', null=True, blank=True) # Изображение продукта