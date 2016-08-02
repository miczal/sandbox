import unittest
from morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def test_latin_separator_should_be_space(self):
        t = MorseLatinTranslator()
        self.assertEqual(t.latin_separator, ' ')

    def test_morse_separator_should_be_vertical_line(self):
        t = MorseLatinTranslator()
        self.assertEqual(t.morse_separator, '|')

