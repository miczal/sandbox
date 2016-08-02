import unittest
from morse.morse import MorseLatinTranslator


class MorseLatinTranslatorTest(unittest.TestCase):

    def test_latin_separator_should_be_space(self):
        t = MorseLatinTranslator()
        self.assertEqual(t.latin_separator, ' ')

