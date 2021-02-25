# ----------------
# Types
# ----------------
# 1. Constant - value (if not name), the required field is optional
# 2. EnumNamespace - an explicitly enumerated set of values, assuming alphabetically ordered
# 3. EnumOrderedNamespace - an explicitly enumerated set of values, explicitly ordered
# 4. FreeFormNamespace - any set of strings
# 5. NumericalSequence - any numerical sequence
# 6. RomanSequence - any roman numeral sequence
# 7. AlphabeticalSequence - any alphabetical sequence
# 8. Composition - any composition of the above level identifiers
# 9. Switch
# ----------------
# <xs:enumeration value="Constant" />
# <xs:enumeration value="EnumNamespace" />
# <xs:enumeration value="EnumOrderedNamespace" />
# <xs:enumeration value="FreeFormNamespace" />
# <xs:enumeration value="NumericalSequence" />
# <xs:enumeration value="RomanSequence" />
# <xs:enumeration value="RegexNamespace" />
# <xs:enumeration value="AlphabeticalSequence" />
# <xs:enumeration value="Composition" />
# <xs:enumeration value="Switch" />

class LevelIdentifier:
    def __init__(self):
        self.type = None
        self.value = None
        self.decoration = None # parenthesis, period, none, any
        self.required = False
        self.target = False
        self.padding_length = None
        self.case = None  # upper, lower, any
        self.examples = []
        self.components = []    # contains LevelIndicators
        self.enums = []   # enum [ fullName, identifier, citationFormat ]

