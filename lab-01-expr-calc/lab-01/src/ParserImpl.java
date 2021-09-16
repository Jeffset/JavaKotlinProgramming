import exceptions.ExpressionParseException;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        return FromPostfixToTree(InfixToPostfix(parseRawString(input)));
    }

    private ArrayList<Expression> parseRawString(String input) throws ExpressionParseException {
        ArrayList<Expression> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ') {
                continue;
            }
            Expression expr;
            if (!isAllowed(c)) {
                throw new ExpressionParseException();
            } else if (Character.isLetter(c)) {
                expr = new VariableLiteral(String.valueOf(c));
            } else if (isLiteral(c)) {
                Vector<Integer> inv_number = new Vector<>();
                inv_number.add(c - '0');
                while (true) {
                    if (i != input.length() - 1 && isLiteral(input.charAt(i + 1))) {
                        i++;
                        inv_number.add(input.charAt(i) - '0');
                    } else {
                        break;
                    }
                }
                int value = 0;
                for (int j = 0; j < inv_number.size(); j++) {
                    value += inv_number.elementAt(j) * Math.pow(10, (inv_number.size() - j - 1));
                }
                expr = new LiteralValue(value);
            } else if (c == '(') {
                expr = new ParenthesisExpression(BraceType.open);
            } else if (c == ')') {
                expr = new ParenthesisExpression(BraceType.close);
            } else {
                BinOpKind opKind = switch (c) {
                    case '+' -> BinOpKind.plus;
                    case '-' -> BinOpKind.minus;
                    case '*' -> BinOpKind.mult;
                    case '/' -> BinOpKind.div;
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                };
                expr = new BinaryExpressionImpl(opKind, null, null);
            }
            result.add(expr);
        }
        return result;
    }

    private ArrayList<Expression> InfixToPostfix(ArrayList<Expression> tokens) {
        ArrayList<Expression> result = new ArrayList<>();
        Stack<Expression> stack = new Stack<>();
        for (var i : tokens) {
            if (i instanceof LiteralValue || i instanceof VariableLiteral) {
                result.add(i);
                continue;
            }
            if (i instanceof ParenthesisExpression brace) {
                if (brace.brace_type == BraceType.open) {
                    stack.push(i);
                } else {
                    while ((!(stack.peek() instanceof ParenthesisExpression)) || (((ParenthesisExpression) stack.peek()).brace_type != BraceType.open)) {
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
        Stack<Expression> st = new Stack<>();
        Expression t;
        for (var i : tokens) {
            if (!(i instanceof LiteralValue || i instanceof VariableLiteral)) {
                Expression t1 = st.pop();
                Expression t2 = st.pop();
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

    boolean isAllowed(char c) {
        return isOperator(c) || isLiteral(c) || c == '(' || c == ')' || Character.isLetter(c);
    }

    boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    boolean isLiteral(char c) {
        return (c - '0' >= 0) && (c - '0' < 10);
    }

    private int Priority(Expression token) {
        if (token instanceof BinaryExpressionImpl expr) {
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
