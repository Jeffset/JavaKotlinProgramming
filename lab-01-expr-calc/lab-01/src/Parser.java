import exceptions.ExpressionParseException;

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}