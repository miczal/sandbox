import string

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
        self.latin_to_morse, self.morse_to_latin = self.create_dictionaries()

    def create_dictionaries(self):
        latin_characters = string.digits + string.ascii_uppercase + self.latin_separator
        morse_characters = ('-----', '.----', '..---', '...--', '....-',
                                '.....', '-....', '--...', '---..', '----.',
                                '.-',    '-...',  '-.-.',  '-..',   '.',
                                '..-.',  '--.',   '....',  '..',    '.---',
                                '-.-',   '.-..',  '--',    '-.',    '---',
                                '.--.',  '--.-',  '.-.',   '...',   '-',
                                '..-',   '...-',  '.--',   '-..-',  '-.--',
                                '--..',  self.morse_separator)
        return dict(zip(latin_characters, morse_characters)), dict(zip(morse_characters, latin_characters))


    def to_morse(self, l):
        return '|'

    def to_latin(self, m):
        return self.morse_to_latin[m]



