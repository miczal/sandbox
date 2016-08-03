import unittest
import string
from .morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def setUp(self):
        self.t = MorseLatinTranslator()

    def test_latin_to_morse_dict_len(self):
        self.assertEqual(len(self.t.latin_to_morse), len(string.digits + string.ascii_uppercase + ' '))

    def test_morse_dict_len_should_be_the_same_as_latin(self):
        self.assertEqual(len(self.t.latin_to_morse), len(self.t.morse_to_latin))

    def test_morse_to_latin_translation_of_a_single_letter_B(self):
        self.assertEqual(self.t.to_latin('-...'), 'B')

    def test_latin_to_morse_translation_of_a_single_letter_B(self):
        self.assertEqual(self.t.to_morse('B'), '-...')

    def test_morse_to_latin_translation_of_a_single_letter_A_with_separator(self):
        self.assertEqual(self.t.to_latin('|.-|'), 'A')

    def test_morse_to_latin_sos_translation(self):
        self.assertEqual(self.t.to_latin('|...|---|...|'), 'SOS')

    def test_morse_to_latin_key_error(self):
        self.assertRaises(KeyError, self.t.to_latin('|...|---|.x.|'))

    def test_latin_to_morse_translation_of_a_single_letter_A_with_separator(self):
        self.assertEqual(self.t.to_morse('A'), '.-')

    def test_latin_to_morse_sos_translation(self):
        self.assertEqual(self.t.to_morse('sOs'), '...|---|...')

    def test_latin_to_morse_key_error(self):
        self.assertRaises(KeyError, self.t.to_latin('ł'))

    def test_equilibrium_of_translation(self):
        s = "RANDOMSTRING"
        self.assertEqual(self.t.to_latin(self.t.to_morse(s)), s)
