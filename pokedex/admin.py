from django.contrib import admin
from .models import Type, Pokemon

# Register your models here.

class TypeAdmin(admin.ModelAdmin):
	filter_horizontal = ('weak_to', 'resistant_to')
class PokemonAdmin(admin.ModelAdmin):
	filter_horizontal = ('poke_type',)

admin.site.register(Type, TypeAdmin)
admin.site.register(Pokemon, PokemonAdmin)