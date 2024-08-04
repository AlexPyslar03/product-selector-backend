from django.urls import path, include
from rest_framework.routers import DefaultRouter
from django.conf import settings
from django.conf.urls.static import static
from . import views
from .views import ProductViewSet

router = DefaultRouter()
router.register(r'products', ProductViewSet)

urlpatterns = [
    path('registration', views.RegisterView),
    path('login', views.LoginView),
    path('user', views.getAllUsers),
    path('user/create', views.addUser),
    path('user/<str:pk>', views.getUser),
    path('user/update/<str:pk>', views.updateUser),
    path('user/delete/<str:pk>', views.deleteUser),
    path('recipe', views.getAllRecipes),
    path('recipe/create', views.addRecipe),
    path('recipe/<str:pk>', views.getRecipe),
    path('recipe/update/<str:pk>', views.updateRecipe),
    path('recipe/delete/<str:pk>', views.deleteRecipe),
    path('product', views.getAllProducts),
    path('product/create', views.addProduct),
    path('product/<str:pk>', views.getProduct),
    path('product/update/<str:pk>', views.updateProduct),
    path('product/delete/<str:pk>', views.deleteProduct),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)