
class MorseLatinTranslator:
    """
    Simple two-way morse-latin translator
    Assumptions:
    - Morse characters separated by |
    - Latin words separated by space
    - Spaces between latin words are 'lost in translation'
    - Latin characters translated from morse will be upper case
    """
    def __init__(self):
        self.latin_separator = ' '
        self.morse_separator = '|'

    def to_morse(self, l):
        return '|'


