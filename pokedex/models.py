from django.db import models
from django.core.exceptions import ValidationError
from colorfield.fields import ColorField
from pathlib import Path
import os

STATIC_PATH = os.path.join(Path(__file__).resolve().parent, 'static')
IMG_PATH = os.path.join(STATIC_PATH, 'img')

class Type(models.Model):
	name = models.CharField(max_length=15, primary_key=True)
	color = ColorField()
	weak_to = models.ManyToManyField('self', blank=True, symmetrical=False)
	resistant_to = models.ManyToManyField('self', related_name='resistant', blank=True, symmetrical=False)
	immune_to = models.ManyToManyField('self', related_name='immune', blank=True, symmetrical=False)

	def __str__(self):
		return self.name

class Pokemon(models.Model):
	pokedex_id = models.IntegerField(primary_key=True)
	name = models.CharField(max_length=100)
	description = models.TextField()
	image = models.FilePathField(path=IMG_PATH)
	poke_type = models.ManyToManyField('Type')

	def __str__(self):
		return self.name

	def is_weak_to_types(self):
		types_weak = set()
		types_resistant = set()
		types_immune = set()
		for t in self.poke_type.all():
			types_weak.update(t.weak_to.all()) 
			types_resistant.update(t.resistant_to.all()) 
			types_immune.update(t.immune_to.all())
		# we want the types in weak but not resistant
		return (types_weak.difference(types_resistant)).difference(types_immune)

	class Meta:
		verbose_name_plural = 'Pokemon'

