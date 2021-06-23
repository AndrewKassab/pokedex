from django.shortcuts import render


# TODO:
def pokedex(request):
	return render(request, 'pokedex.html', {})


# TODO:
def pokedex_entry(request, pk):
	return render(request, 'pokedex_entry.html', {})