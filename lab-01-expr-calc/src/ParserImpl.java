import java.util.Stack;

public class ParserImpl implements Parser {
    enum Operation {
        ADDITION,
        SUBTRACTION;

        private static Operation fromString(String str) {
            switch (str) {
                case "+": {
                    return ADDITION;
                }
                case "-": {
                    return SUBTRACTION;
                }
                default: {
                    assert true : "Invalid operation " + str;
                    return ADDITION;
                }
            }
        }
    }

    public Expression parseExpression(String input) throws ExpressionParseException {
        var operations = new Stack<Operation>();
        var expressions = new Stack<Expression>();
        boolean isPrevLiteral = false;
        for (var strTok : input.split("\\s+")) {
            switch (strTok) {
                case "-":
                case "+": {
                    // unary operation
                    if (!isPrevLiteral) {
                        throwExpressionParseException(input);
                    }
                    isPrevLiteral = false;
                    var newOperation = Operation.fromString(strTok);
                    while (!operations.empty()) {
                        if (expressions.size() < 2) {
                            throwExpressionParseException(input);
                        }
                        var newBinExpr = BinaryExpression.OpType.valueOf(operations.pop().toString());
                        // TODO: remove double of code
                        var secondOperand = expressions.pop();
                        var firstOperand = expressions.pop();
                        expressions.push(new BinaryExpressionImpl(newBinExpr, firstOperand, secondOperand));
                    }
                    operations.push(newOperation);
                    break;
                }
                default: {
                    // literal
                    isPrevLiteral = true;
                    expressions.push(CreateLiteral(strTok));
                    break;
                }
            }
        }

        while(!operations.empty()) {
            var new_operation = operations.pop();
            if (expressions.size() < 2) {
                throwExpressionParseException(input);
            }
            var newBinExpr = BinaryExpression.OpType.valueOf(new_operation.toString());
            var secondOperand = expressions.pop();
            var firstOperand = expressions.pop();
            expressions.push(new BinaryExpressionImpl(newBinExpr, firstOperand, secondOperand));
        }


        if (!operations.empty() || expressions.size() != 1) {
            throwExpressionParseException(input);
        }
        return expressions.pop();
    }

    static private void throwExpressionParseException(String input) throws ExpressionParseException {
        throw new ExpressionParseException("Invalid expression " + input);
    }

    // TODO: move to Literal interface
    static private Literal CreateLiteral(String strLiteral) {
        Double value = ConvertToDouble(strLiteral);
        // TODO: check is literal
        return (value == null ? new Variable(strLiteral) : new Constant(value));
    }

    static private Double ConvertToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
