package pl.wrona.interview

import spock.lang.Specification

class RomanNumberTest extends Specification {

    RomanNumber romanNumber = new RomanNumber()

    def "should parse roman number to integer"() {

        expect:
        romanNumber.romanToInt(ROMAN) == VALUE
        romanNumber.intToRoman(VALUE) == ROMAN

        where:
        ROMAN       || VALUE
        "MMII"      || 2002
        "I"         || 1
        "II"        || 2
        "III"       || 3
        "IV"        || 4
        "V"         || 5
        "VI"        || 6
        "VII"       || 7
        "VIII"      || 8
        "IX"        || 9
        "X"         || 10
        "XX"        || 20
        "XXX"       || 30
        "XL"        || 40
        "L"         || 50
        "LX"        || 60
        "LXX"       || 70
        "LXXX"      || 80
        "XC"        || 90
        "C"         || 100
        "CC"        || 200
        "CCC"       || 300
        "CD"        || 400
        "D"         || 500
        "DC"        || 600
        "DCC"       || 700
        "DCCC"      || 800
        "CM"        || 900
        "M"         || 1000
        "LVIII"     || 58
        "MCMXCIV"   || 1994
        "MMXXII"    || 2022
        "MMMCMXCIX" || 3999
    }
}
