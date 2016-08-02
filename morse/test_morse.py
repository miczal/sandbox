import unittest
from morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def setUp(self):
        self.t = MorseLatinTranslator()

    def test_translation_of_latin_separator(self):
        self.assertEqual(self.t.to_morse(' '), '|')

    def test_translation_of_morse_separator(self):
        self.assertEqual(self.t.to_latin('|'), ' ')
