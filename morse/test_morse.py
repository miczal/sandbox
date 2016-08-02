import unittest
import string
from morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def setUp(self):
        self.t = MorseLatinTranslator()

    def test_translation_of_latin_separator(self):
        self.assertEqual(self.t.to_morse(' '), '|')

    def test_translation_of_morse_separator(self):
        self.assertEqual(self.t.to_latin('|'), ' ')

    def test_latin_to_morse_dict_len(self):
        self.assertEqual(len(self.t.latin_letters), len(string.digits + string.ascii_uppercase + ' '))
