from django.test import TestCase
from .models import Type, Pokemon


class PokemonModelTests(TestCase):

	def test_is_weak_to_single_type(self):
		pass

	def test_is_weak_to_double_type(self):
		pass 

	# Tests when a pokemon is both resistant and weak to a type, then it shouldn't be weak to
	def test_is_weak_to_cancels_out(self):
		pass
