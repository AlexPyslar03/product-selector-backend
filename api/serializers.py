from rest_framework import serializers
from .models import User, Product, Recipe

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

class LoginSerializer(serializers.Serializer):
    username = serializers.CharField(max_length=255)    # логин
    password = serializers.CharField(max_length=128, write_only=True)   # пароль
    token = serializers.CharField(max_length=255, read_only=True)   # токен

    # Аутентификация пользователя и генерация токена
    def validate(self, data):
        username = data['username']
        password = data['password']
        user = authenticate(username=username, password=password)
        if user is None:
            raise serializers.ValidationError("Invalid credentials")
        token = Token.objects.create(user=user)
        return {
            'username': user.username,
            'token': token.key
        }

class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = '__all__'

# Формирование рецепта с использованием паттерна Builder
class RecipeBuilder:
    def __init__(self, recipe_data=None):
        self.recipe_data = recipe_data or {}

    # Название рецепта
    def set_name(self, name):
        self.recipe_data['name'] = name
        return self

    # Описание
    def set_description(self, description):
        self.recipe_data['description'] = description
        return self

    # Ингредиенты
    def set_ingredients(self, ingredients):
        self.recipe_data['ingredients'] = ingredients
        return self

    # Инструкция
    def set_instructions(self, instructions):
        self.recipe_data['instructions'] = instructions
        return self

    # Создаём
    def build(self):
        serializer = RecipeSerializer(data=self.recipe_data)
        if serializer.is_valid():
            return serializer.save()
        else:
            raise serializers.ValidationError(serializer.errors)

class RecipeSerializer(serializers.ModelSerializer):

    class Meta:
        model = Recipe
        fields = '__all__'
