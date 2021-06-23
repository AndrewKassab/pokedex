from django.urls import path 
from . import views 

urlpatterns = [
	path('', views.pokedex, name='pokedex'),
	path('<int:pk>/', views.pokedex_entry, name='pokedex_entry')
]