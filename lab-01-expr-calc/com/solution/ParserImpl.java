package com.solution;

import java.util.Objects;

enum TokenType {
    NONE,
    DELIMITER,
    VARIABLE,
    NUMBER
}

public class ParserImpl implements Parser {
    @Override
    public Expression parseExpression(String input) throws ExpressionParseException {
        mInput = input;
        mCurrentIndex = 0;
        getToken();

        if (mToken.equals(mEndOfExpression)) {
            mHandler.handleException(TypeOfException.NOEXPRESSION);
        }

        Expression result = addOrSubtract();

        if (!mToken.equals(mEndOfExpression)) {
            mHandler.handleException(TypeOfException.SYNTAXERROR);
        }

        return result;
    }

    private Expression addOrSubtract() throws ExpressionParseException {
        Expression left = multiplyOrDivide();
        char operation = mToken.charAt(0);
        while (operation == '-' || operation == '+') {
            getToken();
            Expression right = multiplyOrDivide();
            switch (operation) {
                case '+': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.ADD);
                }
                case '-': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.SUBTRACT);
                }
            }
            operation = mToken.charAt(0);
        }
        return left;
    }

    private Expression multiplyOrDivide() throws ExpressionParseException {
        Expression left = unaryOperators();
        char operation = mToken.charAt(0);
        while (operation == '*' || operation == '/') {
            getToken();
            Expression right = unaryOperators();
            switch (operation) {
                case '*': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.MULTIPLY);
                }
                case '/': {
                    return new BinaryExpressionImpl(left, right, BinOpKind.DIVIDE);
                }
            }
            operation = mToken.charAt(0);
        }
        return left;
    }

    private Expression unaryOperators() throws ExpressionParseException {
        String operation = "";
        if (mTokenType == TokenType.DELIMITER && (Objects.equals(mToken, "-")
                || Objects.equals(mToken, "+"))) {
            operation = mToken;
            getToken();
        }
        Expression result = parenthesisExpression();
        if (operation.equals("-")) {
            result.setUnaryMinus();
        } else if (operation.equals("+")) {
            result.setUnaryPlus();
        }
        return result;
    }

    private Expression parenthesisExpression() throws ExpressionParseException {
        if (mToken.equals("(")) {
            getToken();
            Expression result = addOrSubtract();
            if (!mToken.equals(")")) {
                mHandler.handleException(TypeOfException.UNBALANCEDPARENS);
            }
            getToken();
            return new ParenthesisExpressionImpl(result);
        } else {
            return getLiteral();
        }
    }

    private Expression getLiteral() throws ExpressionParseException {
        if (Character.isLetter(mToken.charAt(0)) || Character.isDigit(mToken.charAt(0))) {
            LiteralImpl result = new LiteralImpl(mToken);
            getToken();
            return result;
        } else if (mToken.equals("+")|| mToken.equals("-")) {
            return unaryOperators();
        } else {
            ExceptionHandler handler = new ExceptionHandler();
            handler.handleException(TypeOfException.SYNTAXERROR);
        }
        return addOrSubtract();
    }

    private void getToken() {
        mTokenType = TokenType.NONE;
        mToken = "";

        if(mCurrentIndex == mInput.length()) {
            mToken = mEndOfExpression;
            return;
        }

        while(mCurrentIndex < mInput.length() && Character.isWhitespace(mInput.charAt(mCurrentIndex))) {
            ++mCurrentIndex;
        }

        if(mCurrentIndex == mInput.length()) {
            mToken = mEndOfExpression;
            return;
        }

        if(isDelim(mInput.charAt(mCurrentIndex))) {
            mToken += mInput.charAt(mCurrentIndex);
            ++mCurrentIndex;
            mTokenType = TokenType.DELIMITER;
        } else if(Character.isLetter(mInput.charAt(mCurrentIndex))) {
            while(!isDelim(mInput.charAt(mCurrentIndex))) {
                mToken += mInput.charAt(mCurrentIndex);
                ++mCurrentIndex;
                if(mCurrentIndex >= mInput.length()) {
                    break;
                }
            }
            mTokenType = TokenType.VARIABLE;
        } else if (Character.isDigit(mInput.charAt(mCurrentIndex))) {
            while(!isDelim(mInput.charAt(mCurrentIndex))){
                mToken += mInput.charAt(mCurrentIndex);
                ++mCurrentIndex;
                if(mCurrentIndex >= mInput.length()) {
                    break;
                }
            }
            mTokenType = TokenType.NUMBER;
        } else {
            mToken = mEndOfExpression;
        }
    }

    private boolean isDelim(char charAt) {
        return (" +-/*()".indexOf(charAt)) != -1;
    }

    private String mInput;
    private int mCurrentIndex;
    private String mToken;
    TokenType mTokenType;
    private final ExceptionHandler mHandler = new ExceptionHandler();

    private final String mEndOfExpression = "\0";
}