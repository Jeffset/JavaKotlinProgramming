import exceptions.ExpressionParseException;

import java.util.Stack;

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        Stack<Expression> st = new Stack();
        Expression t, t1, t2;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                i++;
            }
            if (i == input.length()) {
                break;
            }
            if (!isOperator(input.charAt(i))) {
                t = new LiteralValue(input.charAt(i) - '0');
                st.push(t);
            } else {
                t1 = st.pop();
                t2 = st.pop();
                if (input.charAt(i) == '+') {
                    t = new BinaryExpressionImpl(BinOpKind.plus, t2, t1);
                } else if (input.charAt(i) == '-') {
                    t = new BinaryExpressionImpl(BinOpKind.minus, t2, t1);
                } else {
                    throw new ExpressionParseException();
                }
                st.push(t);
            }
        }
        t = st.peek();
        st.pop();
        return t;
    }

    boolean isOperator(char c) {
        return c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^';
    }
}
