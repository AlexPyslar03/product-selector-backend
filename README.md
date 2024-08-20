# Product Selector Backend
**Product Selector Backend** — это RESTful веб-сервис для управления пользователями, продуктами и рецептами. Он предоставляет функции для создания, чтения, обновления и удаления данных о пользователях, продуктах и рецептах.

## Оглавление
- [Особенности](#особенности)
- [Установка и запуск](#установка-и-запуск)
- [API](#api)
  - [Пользователи](#пользователи)
  - [Продукты](#продукты)
  - [Рецепты](#рецепты)
- [Тестирование](#тестирование)
## Особенности
- CRUD операции для пользователей, продуктов и рецептов.
- Поддержка связи между продуктами и рецептами.
- Интерактивная документация API с помощью Swagger UI.
## Установка и запуск
### Требования
- Java 17 или выше
- Gradle 8.x или выше
### Клонирование репозитория
``` bash
git clone https://github.com/your-username/product-selector-backend.git
cd product-selector-backend
```
### Настройка
1) Настройка базы данных: Убедитесь, что у вас запущен сервер базы данных. Измените параметры подключения в src/main/resources/application.properties.
2) Сборка проекта:
``` bash
./gradlew build
```
3) Запуск приложения:
``` bash
./gradlew bootRun
```
4) Приложение будет доступно по адресу `http://localhost:8081` по умолчанию.

## API
### Пользователи
#### Создание нового пользователя
- URL: `/users`
- Метод: `POST`
- Тело запроса:
``` json
{
"name": "John Doe",
"surname": "Doe",
"email": "john.doe@example.com",
"password": "securepassword",
"birthDate": "1990-01-01",
"registrationDate": "2024-01-01",
"accessLevel": "USER"
}
```
#### Получение всех пользователей
- URL: `/users`
- Метод: `GET`
#### Получение пользователя по ID
- URL: `/users/{id}`
- Метод: `GET`
#### Получение пользователей по списку ID
- URL: `/users/batch`
- Метод: `GET`
- Параметры: `ids` (список идентификаторов)
#### Обновление пользователя
- URL: `/users`
- Метод: `PUT`
- Тело запроса:
``` json
{
"id": 1,
"name": "John Doe",
"surname": "Doe",
"email": "john.doe@newemail.com",
"password": "newsecurepassword",
"birthDate": "1990-01-01",
"registrationDate": "2024-01-01",
"accessLevel": "USER"
}
```
#### Удаление пользователя
- URL: `/users/{id}`
- Метод: `DELETE`
### Продукты
#### Создание нового продукта
- URL: `/products`
- Метод: `POST`
- Тело запроса:
```json
{
"name": "Apple",
"image": "http://example.com/apple.jpg",
"recipeIds": [1, 2]
}
```
#### Получение всех продуктов
- URL: `/products`
- Метод: `GET`
#### Получение продукта по ID
- URL: `/products/{id}`
- Метод: `GET`
#### Получение продуктов по списку ID
-URL: `/products/batch`
- Метод: `GET`
Параметры: `ids` (список идентификаторов)
#### Получение продуктов по ID рецепта
- URL: `/products/recipe/{id}`
- Метод: `GET`
#### Обновление продукта
- URL: `/products`
- Метод: `PUT`
- Тело запроса:
```json
{
"id": 1,
"name": "Green Apple",
"image": "http://example.com/green-apple.jpg",
"recipeIds": [1, 3]
}
```
#### Удаление продукта
- URL: `/products/{id}`
- Метод: `DELETE`
### Рецепты
#### Создание нового рецепта
- URL: `/recipes`
- Метод: `POST`
- Тело запроса:
```json
{
"name": "Apple Pie",
"description": "A delicious apple pie recipe.",
"productIds": [1, 2]
}
```
#### Получение всех рецептов
- URL: `/recipes`
- Метод: `GET`
#### Получение рецепта по ID
- URL: `/recipes/{id}`
- Метод: `GET`
#### Получение рецептов по списку ID
- URL: `/recipes/batch`
- Метод: `GET`
- Параметры: `ids` (список идентификаторов)
#### Получение рецептов по ID продукта
- URL: `/recipes/product/{id}`
- Метод: `GET`
#### Обновление рецепта
- URL: `/recipes`
- Метод: `PUT`
- Тело запроса:
```json
{
"id": 1,
"name": "Updated Apple Pie",
"description": "An updated apple pie recipe.",
"productIds": [1, 3]
}
```
#### Удаление рецепта
- URL: `/recipes/{id}`
- Метод: `DELETE`
## Тестирование
Для запуска тестов используйте следующую команду:
```bash
./gradlew test
```