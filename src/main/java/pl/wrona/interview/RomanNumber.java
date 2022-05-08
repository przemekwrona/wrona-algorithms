package pl.wrona.interview;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://leetcode.com/problems/integer-to-roman/
 * https://leetcode.com/problems/roman-to-integer/
 */
public class RomanNumber {

    public int romanToInt(String roman) {
        int returnValue = 0;

        int currentIndex = 0;
        int totalLength = roman.length();

        for (RomanGroup romanGroup : RomanGroup.getPatterns()) {
            RomanPattern romanPattern = findRomanPattern(roman.substring(currentIndex, totalLength), romanGroup);
            int number = romanPattern.number(romanGroup);
            returnValue = returnValue + number;
            currentIndex = currentIndex + romanPattern.getPatternSize();
        }

        return returnValue;
    }

    public String intToRoman(int num) {
        StringBuilder builder = new StringBuilder();

        for (RomanGroup romanGroup : RomanGroup.getPatterns()) {
            RomanPattern romanPattern = RomanPattern.checkPattern(num, romanGroup);
            builder.append(romanPattern.build(romanGroup));
            num = num - romanPattern.number(romanGroup);
        }

        return builder.toString();
    }

    private RomanPattern findRomanPattern(String roman, RomanGroup pattern) {
        for (int windowSize = Math.min(roman.length(), 5); windowSize > 0; windowSize--) {
            RomanPattern romanPattern = RomanPattern.checkPattern(roman.substring(0, windowSize), pattern);
            if (!RomanPattern.UNDEFINED.equals(romanPattern)) {
                return romanPattern;
            }
        }
        return RomanPattern.UNDEFINED;
    }

    private enum RomanGroup {
        M(new RomanNumberEnum[]{RomanNumberEnum.M, RomanNumberEnum.UNDEFINED, RomanNumberEnum.UNDEFINED}, 3),
        C(new RomanNumberEnum[]{RomanNumberEnum.C, RomanNumberEnum.D, RomanNumberEnum.M}, 2),
        X(new RomanNumberEnum[]{RomanNumberEnum.X, RomanNumberEnum.L, RomanNumberEnum.C}, 1),
        I(new RomanNumberEnum[]{RomanNumberEnum.I, RomanNumberEnum.V, RomanNumberEnum.X}, 0);

        private final RomanNumberEnum[] romanNumberGroup;

        private final int pow;

        RomanGroup(RomanNumberEnum[] romanNumberGroup, int pow) {
            this.romanNumberGroup = romanNumberGroup;
            this.pow = pow;
        }

        public RomanNumberEnum[] getRomanNumberGroup() {
            return romanNumberGroup;
        }

        public int getPow() {
            return pow;
        }

        public static List<RomanGroup> getPatterns() {
            return Stream.of(RomanGroup.values())
                    .map(pattern -> List.of(pattern, pattern))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        public int get10pow() {
            return (int) Math.pow(10, pow);
        }

    }

    private enum RomanNumberEnum {
        I("I", 1),
        V("V", 5),
        X("X", 10),
        L("L", 50),
        C("C", 100),
        D("D", 500),
        M("M", 1000),
        UNDEFINED("", 10000);

        private final String romanValue;

        private final int value;

        RomanNumberEnum(String romanValue, int value) {
            this.romanValue = romanValue;
            this.value = value;
        }

        public String getRomanValue() {
            return romanValue;
        }

    }

    private enum RomanPattern {
        I("%s", 1, 1),
        II("%s%s", 2, 2),
        III("%s%s%s", 3, 3),
        IV("%s%s", 4, 2),
        V("%s", 5, 1),
        VI("%s%s", 6, 2),
        VII("%s%s%s", 7, 3),
        VIII("%s%s%s%s", 8, 4),
        IX("%s%s", 9, 2),
        X("%s", 10, 1),
        UNDEFINED("", 0, 0);

        private final String pattern;

        private final int number;

        private final int patternSize;

        RomanPattern(String pattern, int number, int patternSize) {
            this.pattern = pattern;
            this.number = number;
            this.patternSize = patternSize;
        }

        public int getNumber() {
            return number;
        }

        public int getPatternSize() {
            return patternSize;
        }

        public String build(RomanGroup romanGroup) {
            String FIRST = romanGroup.getRomanNumberGroup()[0].getRomanValue();
            String SECOND = romanGroup.getRomanNumberGroup()[1].getRomanValue();
            String THIRD = romanGroup.getRomanNumberGroup()[2].getRomanValue();

            if (this.equals(RomanPattern.I)) {
                return RomanPattern.I.format(FIRST, FIRST);
            } else if (this.equals(RomanPattern.II)) {
                return RomanPattern.II.format(FIRST, FIRST);
            } else if (this.equals(RomanPattern.III)) {
                return RomanPattern.III.format(FIRST, FIRST, FIRST);
            } else if (this.equals(RomanPattern.IV)) {
                return RomanPattern.IV.format(FIRST, SECOND);
            } else if (this.equals(RomanPattern.V)) {
                return RomanPattern.V.format(SECOND);
            } else if (this.equals(RomanPattern.VI)) {
                return RomanPattern.VI.format(SECOND, FIRST);
            } else if (this.equals(RomanPattern.VII)) {
                return RomanPattern.VII.format(SECOND, FIRST, FIRST);
            } else if (this.equals(RomanPattern.VIII)) {
                return RomanPattern.VIII.format(SECOND, FIRST, FIRST, FIRST);
            } else if (this.equals(RomanPattern.IX)) {
                return RomanPattern.IX.format(FIRST, THIRD);
            } else if (this.equals(RomanPattern.X)) {
                return RomanPattern.X.format(THIRD);
            }
            return "";
        }

        public static RomanPattern checkPattern(String value, RomanGroup group) {
            return Stream.of(RomanPattern.values())
                    .filter(pattern -> value.equals(pattern.build(group)))
                    .findFirst()
                    .orElse(RomanPattern.UNDEFINED);
        }

        public static RomanPattern checkPattern(int value, RomanGroup pattern) {
            return Stream.of(RomanPattern.values())
                    .filter(r -> value >= r.getNumber() * pattern.get10pow())
                    .max(Comparator.comparingInt(RomanPattern::getNumber))
                    .orElse(RomanPattern.UNDEFINED);
        }

        private String format(String... args) {
            return String.format(this.pattern, (Object[]) args);
        }

        public int number(RomanGroup romanGroup) {
            return number * (int) Math.pow(10, romanGroup.getPow());
        }
    }
}
