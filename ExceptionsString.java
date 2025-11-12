package exceptions;

public class ExceptionsString {
    public static void main(String[] args) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        String equation = "5*2-2*3=4";
        StringCalculator cs = new StringCalculator();
        System.out.println(cs.compareNumbers(equation));

    }
}

class StringCalculator {
    private static final int MAX_OPERAND_NUMBER = 200;

    String[] splitEquation(String equation) {
        String splitRegex = "=";
        return equation.split(splitRegex);
    }

    int evaluateExpression(String equation) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        int temp = 0;
        int result = 0;
        String operator = "+";
        String[] equationSplit = equation.split("(?=[/+\\-*])|(?<=[/+\\-*])");
        int count = equationSplit.length;
        checkAllExceptions(equation);
        for (int i = 0; i < equationSplit.length; i++) {
            if (equationSplit[i].matches("[/+\\-*]") && equationSplit[i + 1].matches("-")) {
                equationSplit[i + 2] = equationSplit[i + 1] + equationSplit[i + 2];
                equationSplit[i + 1] = "";

            }
        }

        for (int i = 0; i < equationSplit.length; i++) {
            if (equationSplit[i].isEmpty()) {
                count--;
            }
        }
        String[] secondEquationSplit = new String[count];
        int index = 0;
        for (int i = 0; i < equationSplit.length; i++) {
            if (!equationSplit[i].isEmpty()) {
                secondEquationSplit[index] = equationSplit[i];
                index++;
            }
        }
        int left;
        int right;

        for (int i = 0; i < secondEquationSplit.length; i++) {
            if (secondEquationSplit[i].equals("*") || secondEquationSplit[i].equals("/")) {
                left = Integer.parseInt(secondEquationSplit[i - 1]);
                right = Integer.parseInt(secondEquationSplit[i + 1]);
                if (secondEquationSplit[i].equals("*")) {
                    temp = left * right;
                    secondEquationSplit[i + 1] = String.valueOf(temp);
                    secondEquationSplit[i] = "";
                    secondEquationSplit[i - 1] = "";
                } else if (secondEquationSplit[i].equals("/")) {
                    temp = left / right;
                    secondEquationSplit[i + 1] = String.valueOf(temp);
                    secondEquationSplit[i] = "";
                    secondEquationSplit[i - 1] = "";
                }
            }
        }
        int secondCount = secondEquationSplit.length;
        for (int i = 0; i < secondEquationSplit.length; i++) {
            if (secondEquationSplit[i].isEmpty()) {
                secondCount--;
            }

        }
        String[] newEquationSplit = new String[secondCount];
        int secondIndex = 0;
        for (int i = 0; i < secondEquationSplit.length; i++) {
            if (!secondEquationSplit[i].isEmpty()) {
                newEquationSplit[secondIndex] = secondEquationSplit[i];
                secondIndex++;
            }
        }

        // массив с пустыми строками -> считаю количество пустых строк -> созадю новый массив поменьше -> переношу в него все кроме пустых строк

        for (String equationCalc : newEquationSplit) {


            switch (equationCalc) {
                case "+":
                    operator = "+";
                    break;
                case "-":
                    operator = "-";
                    break;
            }
            if (!equationCalc.matches("-?\\d+")) {

                equationCalc = equationCalc.replaceAll("[+-]", "");
                if (equationCalc.isEmpty()) {
                    continue;
                }
            }
            int equationNumber = Integer.parseInt(equationCalc);
            switch (operator) {
                case "+":
                    result += equationNumber;
                    break;
                case "-":
                    result -= equationNumber;
                    break;
            }

        }
        return result;
    }


    boolean compareNumbers(String equation) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        String[] equationArray = splitEquation(equation);
        int leftPart = evaluateExpression(equationArray[0]);
        int rightPart = evaluateExpression(equationArray[1]);
        return leftPart == rightPart;

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

    void validateOperandCount(String equation) throws TooManyArgumentsException {
        String[] equationSplit = equation.split("(?=[+\\-*/])|(?<=[+\\-*/])");
        int count = 0;
        for (String s : equationSplit) {
            if (s.matches("\\d+$")) {
                count++;
            }
            if (count > MAX_OPERAND_NUMBER) {
                throw new TooManyArgumentsException("Слишком Длинное выражение");
            }
        }
    }

    void checkAllExceptions(String equation) throws InvalidSignException,
            FloatNumbersException,
            TooManyArgumentsException {
        validateSign(equation);
        validateIfInteger(equation);
        validateOperandCount(equation);
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
