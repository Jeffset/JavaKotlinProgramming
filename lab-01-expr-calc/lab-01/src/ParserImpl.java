import exceptions.ExpressionParseException;

import java.util.ArrayList;
import java.util.Stack;

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        return FromPostfixToTree(InfixToPostfix(parseRawString(input)));
    }

    boolean isAllowed(char c) {
        return isOperator(c) || isLiteral(c) || c == '(' || c == ')';
    }

    boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    boolean isLiteral(char c) {
        return (c - '0' >= 0) && (c - '0' < 10);
    }

    private ArrayList<Expression> parseRawString(String input) throws ExpressionParseException {
        ArrayList<Expression> result = new ArrayList<Expression>();
        for (int i = 0; i < input.length(); i++) {
            Expression expr;
            char c = input.charAt(i);
            if (!isAllowed(c)) {
                throw new ExpressionParseException();
            } else if (isLiteral(c)) {
                expr = new LiteralValue(c - '0');
            } else if (c == '(') {
                expr = new ParenthesisExpression(BraceType.open);
            } else if (c == ')') {
                expr = new ParenthesisExpression(BraceType.close);
            } else {
                BinOpKind opKind;
                switch (c) {
                    case '+':
                        opKind = BinOpKind.plus;
                        break;
                    case '-':
                        opKind = BinOpKind.minus;
                        break;
                    case '*':
                        opKind = BinOpKind.mult;
                        break;
                    case '/':
                        opKind = BinOpKind.div;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + c);
                }
                expr = new BinaryExpressionImpl(opKind, null, null);
            }
            result.add(expr);
        }
        return result;
    }

    private ArrayList<Expression> InfixToPostfix(ArrayList<Expression> tokens) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        Stack<Expression> stack = new Stack<Expression>();
        for (var i : tokens) {
            if (i instanceof LiteralValue) {
                result.add(i);
                continue;
            }
            if (i instanceof ParenthesisExpression) {
                ParenthesisExpression brace = (ParenthesisExpression) i;
                if (brace.brace_type == BraceType.open) {
                    stack.push(i);
                } else {
                    while (true) {
                        if ((stack.peek() instanceof ParenthesisExpression) && (((ParenthesisExpression) stack.peek()).brace_type == BraceType.open)) {
                            break;
                        }
                        result.add(stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                }
                continue;
            }
            if (i instanceof BinaryExpressionImpl) {
                while (((!stack.empty() && Priority(stack.peek()) != -1 && Priority(stack.peek()) >= Priority(i)))) {
                    result.add(stack.peek());
                    stack.pop();
                }
                stack.push(i);
            }
        }
        while (!stack.empty()) {
            result.add(stack.peek());
            stack.pop();
        }
        return result;
    }

    private Expression FromPostfixToTree(ArrayList<Expression> tokens) {
        Stack<Expression> st = new Stack<Expression>();
        Expression t, t1, t2;
        for (var i : tokens) {
            if (!(i instanceof LiteralValue)) {
                t1 = st.pop();
                t2 = st.pop();
                BinaryExpressionImpl temp = (BinaryExpressionImpl) i;
                t = new BinaryExpressionImpl(temp.getOperation(), t2, t1);
                st.push(t);
            } else {
                st.push(i);
            }
        }
        t = st.peek();
        st.pop();
        return t;
    }

    private int Priority(Expression token) {
        if (token instanceof BinaryExpressionImpl) {
            BinaryExpressionImpl expr = (BinaryExpressionImpl) token;
            if (expr.getOperation() == BinOpKind.mult
                    || expr.getOperation() == BinOpKind.div) {
                return 1;
            } else if (expr.getOperation() == BinOpKind.plus
                    || expr.getOperation() == BinOpKind.minus) {
                return 0;
            }
        } else {
            return -1;
        }
        return -1;
    }
}
