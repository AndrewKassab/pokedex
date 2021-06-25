from django.test import TestCase
from .models import Type, Pokemon


class PokemonModelTests(TestCase):

	fixtures = ['types.json', 'pokemon.json']

	# TODO: Improve once you have all types seeded
	def test_is_weak_to_single_type(self):
		squirtle = Pokemon.objects.get(pk=7)
		grass = Type.objects.get(pk="Grass")
		expected_weak_to = [grass]
		returned_weak_to = list(squirtle.is_weak_to_types())
		self.assertEqual(returned_weak_to, expected_weak_to)


	def test_is_weak_to_double_type(self):
		pass 

	# Tests when a pokemon is both resistant and weak to a type, then it shouldn't be weak to
	def test_is_weak_to_cancels_out(self):
		pass
