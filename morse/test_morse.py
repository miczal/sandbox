import unittest
from morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def setUp(self):
        self.t = MorseLatinTranslator()

    def test_latin_separator_should_be_space(self):
        self.assertEqual(self.t.latin_separator, ' ')

    def test_morse_separator_should_be_vertical_line(self):
        self.assertEqual(self.t.morse_separator, '|')

