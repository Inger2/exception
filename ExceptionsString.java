package exceptions;

import java.util.Arrays;

public class ExceptionsString {
    public static void main(String[] args) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        String equation = "34+435-20=449+20-19";
        StringCalculator cs = new StringCalculator();
        System.out.println(cs.stringCompare(equation));

    }
}

class StringCalculator {
    private static final int MAX_OPERAND_NUMBER = 200;

    String[] splitEquation(String equation) {
        String splitRegex = "=";
        return equation.split(splitRegex);
    }

    boolean stringCompare(String equation) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        String[] equationArray = splitEquation(equation);
        String leftPart = equationArray[0];
        String rightPart = equationArray[1];
        String[] leftPartArray = leftPart.replaceAll("\\s", "").split("\\+|(?=-)");
        String[] rightPartArray = rightPart.replaceAll("\\s", "").split("\\+|(?=-)");
        checkAllExceptions(equation);
        return Arrays
                .stream(leftPartArray)
                .mapToInt(Integer::parseInt).sum() == Arrays
                .stream(rightPartArray)
                .mapToInt(Integer::parseInt).sum();
    }

    void validateSign(String equation) throws InvalidSignException {
        if (equation.matches(".*[^-+*/=0-9.].*")) {
            throw new InvalidSignException("Неправильный знак");
        }
    }

    void validateIfInteger(String equation) throws FloatNumbersException {
        if (equation.contains(".")) {
            throw new FloatNumbersException("Число не может быть дробным");
        }
    }

    void ValidateOperandCount(String equation) throws TooManyArgumentsException {
        String[] arrayEquation = splitEquation(equation);
        String splitRegex = "[-+*/]";
        String[] leftPart = arrayEquation[0].split(splitRegex);
        String[] rightPart = arrayEquation[1].split(splitRegex);
        if (leftPart.length > MAX_OPERAND_NUMBER || rightPart.length > MAX_OPERAND_NUMBER) {
            throw new TooManyArgumentsException("Слишком длинное выражение");
        }
    }

    void checkAllExceptions(String equation) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        validateSign(equation);
        validateIfInteger(equation);
        ValidateOperandCount(equation);
    }

}

class InvalidSignException extends Exception {
    public InvalidSignException(String message) {
        super(message);

    }
}

class FloatNumbersException extends Exception {
    public FloatNumbersException(String message) {
        super(message);
    }
}

class TooManyArgumentsException extends Exception {
    public TooManyArgumentsException(String message) {
        super(message);
    }
}
