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
        self.assertEqual(len(self.t.latin_characters), len(string.digits + string.ascii_uppercase + ' '))

    def test_morse_dict_len_should_be_the_same_as_latin(self):
        self.assertEqual(len(self.t.latin_characters), len(self.t.morse_characters))

    def test_morse_to_latin_translation_of_a_single_letter_B(self):
        self.assertEqual(self.t.to_latin('B'), '-...')
