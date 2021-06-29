from django.shortcuts import render
from .models import Pokemon


# TODO:
def pokedex(request):
	pokemon = Pokemon.objects.all()
	context = {
		"pokemon": pokemon 
	}
	print(pokemon)
	return render(request, 'pokedex.html', context)


# TODO:
def pokedex_entry(request, pk):
	return render(request, 'pokedex_entry.html', {})