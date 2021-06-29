from django.shortcuts import render
from .models import Pokemon


def pokedex(request):
	pokemon = Pokemon.objects.all()
	context = {
		"pokemon": pokemon 
	}
	print(pokemon)
	return render(request, 'pokedex.html', context)


def pokedex_entry(request, pk):
	pokemon = Pokemon.objects.get(pk=pk)
	context = {
		"pokemon": pokemon
	}
	return render(request, 'pokedex_entry.html', context)