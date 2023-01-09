package ru.kata;

import java.util.Scanner;

public class Main {
    enum TypeOperand {
        ROME("rome"), NUMBER("number");
        final String type;

        TypeOperand(String type) {
            this.type = type;
        }

        public boolean is(TypeOperand typeOperand) {
            return this.equals(typeOperand);
        }
    }

    public static String operand1;
    public static String operand2;

    public static final Character[] ROMAN_CHARS = {'I', 'V', 'X'};

    static String[] arrayOfNumbers;
    static char[] charOperation;
    static char operation;
    static int a;
    static int b;
    static TypeOperand typeOperand;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        charOperation = new char[10];
        String input = scanner.nextLine();
        System.out.println(calc(input));

    }

    public static String calc(String input) throws Exception {
        int result = 0;
        arrayOfNumbers = input.split("[+\\-*/]");
        if (arrayOfNumbers.length <= 1) {
            throw new Exception("т.к. строка не является математической операцией");
        }
        if (arrayOfNumbers.length > 2) {
            throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        operand1 = arrayOfNumbers[0].trim();
        operand2 = arrayOfNumbers[1].trim();


        if (input.startsWith("I") | input.startsWith("V") | input.startsWith("X")) {
            typeOperand = TypeOperand.ROME;
        } else if (Character.isDigit(input.charAt(0))) {
            typeOperand = TypeOperand.NUMBER;
        }

        checkTypeNumber(operand1, operand2, typeOperand);

        // определение операции (предварительно необходимо проверить кол-во операндов
        for (int i = 0; i < input.length(); i++) {
            charOperation[i] = input.charAt(i);
            if (charOperation[i] == '-' | charOperation[i] == '+' | charOperation[i] == '*' | charOperation[i] == '/') {
                operation = charOperation[i];
                break;
            }
        }

        if (TypeOperand.ROME.is(typeOperand)) {
            a = convert(operand1);
            b = convert(operand2);
        } else {
            a = Integer.parseInt(operand1);
            b = Integer.parseInt(operand2);
        }
        switch (operation) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
        }

        // проверка типа операнда
        if (TypeOperand.ROME.is(typeOperand)) {
            if (result < 1) {
                throw new Exception("в римской системе нет отрицательных чисел");
            } else
                return convertRoman(result);
        } else
            return String.valueOf(result);
    }

    public static int convert(String rome) throws Exception {
        switch (rome) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
            default:
                throw new Exception("Калькулятор принимает на вход числа от I до X включительно, не более.");
        }
    }

    private static String convertRoman(int arabian) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
                "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX",
                "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI",
                "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV",
                "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV",
                "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV",
                "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI",
                "XCVII", "XCVIII", "XCIX", "C"
        };
        return roman[arabian];
    }

    public static boolean isVailableNumber(int a, int b) {
        return (a >= 1 && a <= 10) && (b >= 1 && b <= 10);
    }

    public static void checkTypeNumber(String a, String b, TypeOperand type) throws Exception {
        if (TypeOperand.ROME.is(type)) {
            checkCorrectRomanChars(a);
            checkCorrectRomanChars(b);
            checkDiapason(isVailableNumber(convert(a), convert(b)), type);
        } else if (TypeOperand.NUMBER.is(type)) {
            try {
                checkDiapason(isVailableNumber(Integer.parseInt(a), Integer.parseInt(b)), type);
            } catch (NumberFormatException e) {
                throw new Exception("т.к. используются одновременно разные системы счисления");
            }
        }
    }

    private static void checkCorrectRomanChars(String b) throws Exception {
        for (char c : b.toCharArray()) {
            boolean correctChar = false;
            for (Character romanChar : ROMAN_CHARS) {
                if (c == romanChar) {
                    correctChar = true;
                    break;
                }
            }
            if (!correctChar) {
                throw new Exception("т.к. используются одновременно разные системы счисления");
            }
        }
    }

    public static void checkDiapason(Boolean bool, TypeOperand type) throws Exception {
        String min;
        String max;
        if (TypeOperand.NUMBER.is(type)) {
            min = "1";
            max = "10";
        } else {
            min = "I";
            max = "X";
        }
        if (!bool)
            throw new Exception("Калькулятор принимает на вход числа от " + min + " до " + max + " включительно, не более.");
    }
}