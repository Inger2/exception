package exceptions;

import java.util.Arrays;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExceptionsString {
    public static void main(String[] args) throws InvalidSignException, FloatNumbersException, TooManyArgumentsException, ScriptException {
        String equation = "34+435=44+20";
        StringCalculator cs = new StringCalculator();
        System.out.println(cs.stringCompare(equation));

    }
}

class StringCalculator {
    String[] splitEquation(String equation) {
        String splitRegex = "=";
        return equation.split(splitRegex);
    }

    boolean stringCompare(String equation) throws ScriptException, InvalidSignException, FloatNumbersException, TooManyArgumentsException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String[] equationArray = splitEquation(equation);
        String leftPart = equationArray[0];
        String rightPart = equationArray[1];
        checkAllExceptions(equation);
    return ((int) engine.eval(leftPart) == (int) engine.eval(rightPart));
    }

    void invalidSignException(String equation) throws InvalidSignException {
        if (equation.matches(".*[^-+*/=0-9.].*")) {
            throw new InvalidSignException("Неправильный знак");
        }
    }

    void floatNumberException(String equation) throws FloatNumbersException {
        if (equation.contains(".")) {
            throw new FloatNumbersException("Число не может быть дробным");
        }
    }

    void tooManyArgumentsException(String equation) throws TooManyArgumentsException {
        String[] arrayEquation = splitEquation(equation);
        String splitRegex = "[-+*/]";
        String[] leftPart = arrayEquation[0].split(splitRegex);
        String[] rightPart = arrayEquation[1].split(splitRegex);
        if (leftPart.length > 200 || rightPart.length > 200) {
            throw new TooManyArgumentsException("Слишком длинное выражение");
        }
    }
    void checkAllExceptions(String equation) throws InvalidSignException, FloatNumbersException, TooManyArgumentsException {
        invalidSignException(equation);
        floatNumberException(equation);
        tooManyArgumentsException(equation);
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
