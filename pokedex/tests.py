from django.test import TestCase
from .models import Type, Pokemon


class PokemonModelTests(TestCase):

	fixtures = ['types.json', 'pokemon.json']

	def test_is_weak_to_single_type(self):
		squirtle = Pokemon.objects.get(pk=7)
		expected_weak_to = list(Type.objects.get(pk='Water').weak_to.all())
		returned_weak_to = list(squirtle.is_weak_to_types())
		self.assertCountEqual(returned_weak_to, expected_weak_to)


	def test_is_weak_to_double_type(self):
		charizard = Pokemon.objects.get(pk=6)
		water = Type.objects.get(pk='Water')
		electric = Type.objects.get(pk='Electric')
		rock = Type.objects.get(pk='Rock')
		expected_weak_to = [water, electric, rock]
		returned_weak_to = list(charizard.is_weak_to_types())
		self.assertCountEqual(returned_weak_to, expected_weak_to)

	# Tests when a pokemon is both resistant and weak to a type, then it shouldn't be weak to
	def test_is_weak_to_cancels_out(self):
		pidgeot = Pokemon.objects.get(pk=18)
		fighting = Type.objects.get(pk='Fighting')
		returned_weak_to = list(pidgeot.is_weak_to_types())
		self.assertNotIn(fighting, returned_weak_to)
