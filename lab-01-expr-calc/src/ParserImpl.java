import java.util.Stack;

public class ParserImpl implements Parser {
    enum Operation {
        ADDITION(3),
        SUBTRACTION(3),
        MULTIPLICATION(2),
        DIVISION(2);

        private final int mPriority;  // lower = more priority

        Operation(int priority) {
            mPriority = priority;
        }

        static boolean isMorePriority(Operation lhs, Operation rhs) {
            return lhs.mPriority < rhs.mPriority;
        }

        static boolean isEqualPriority(Operation lhs, Operation rhs) {
            return lhs.mPriority == rhs.mPriority;
        }

        static boolean isNotLessPriority(Operation lhs, Operation rhs) {
            return lhs.mPriority <= rhs.mPriority;
        }

        private static Operation fromString(String str) {
            switch (str) {
                case "+": {
                    return ADDITION;
                }
                case "-": {
                    return SUBTRACTION;
                }
                case "*": {
                    return MULTIPLICATION;
                }
                case "/": {
                    return DIVISION;
                }
                default: {
                    assert true : "Invalid operation " + str;
                    return ADDITION;
                }
            }
        }
    }

    static private void processOperations(Stack<Operation> operations,
                                          Stack<Expression> expressions,
                                          String input) throws ExpressionParseException {
        if (expressions.size() < 2) {
            throwExpressionParseException(input);
        }
        var newBinExpr = BinaryExpression.OpType.valueOf(operations.pop().toString());
        var secondOperand = expressions.pop();
        var firstOperand = expressions.pop();
        expressions.push(new BinaryExpressionImpl(newBinExpr, firstOperand, secondOperand));
    }

    public Expression parseExpression(String input) throws ExpressionParseException {
        var operations = new Stack<Operation>();
        var expressions = new Stack<Expression>();
        boolean isPrevVariableOrConstant = false;
        for (var strTok : input.split("\\s+")) {
            switch (strTok) {
                case "-":
                case "+":
                case "*":
                case "/": {
                    // unary operation
                    if (!isPrevVariableOrConstant) {
                        throwExpressionParseException(input);
                    }
                    isPrevVariableOrConstant = false;
                    var newOperation = Operation.fromString(strTok);
                    while (!operations.empty() && Operation.isNotLessPriority(operations.peek(), newOperation)) {
                        processOperations(operations, expressions, input);
                    }
                    operations.push(newOperation);
                    break;
                }
                default: {
                    // variable or constant
                    isPrevVariableOrConstant = true;
                    expressions.push(CreateVariableOrConstant(strTok));
                    break;
                }
            }
        }

        while(!operations.empty()) {
            processOperations(operations, expressions, input);
        }


        if (!operations.empty() || expressions.size() != 1) {
            throwExpressionParseException(input);
        }
        return expressions.pop();
    }

    static private void throwExpressionParseException(String input) throws ExpressionParseException {
        throw new ExpressionParseException("Invalid expression " + input);
    }

    static boolean isVariable(String str) {
        assert str != null : "";
        return (!str.isEmpty() && Character.isLetter(str.charAt(0)));
    }

    // TODO: move to Literal interface
    static private Expression CreateVariableOrConstant(String str) throws ExpressionParseException {
        Double value = convertToDouble(str);
        if (value != null) {
            return new LiteralImpl(value);
        }
        if (isVariable(str)) {
            return new VariableImpl(str);
        }
        throwExpressionParseException(str);
        return null;
    }

    static private Double convertToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
