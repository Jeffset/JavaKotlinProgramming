import java.util.Map;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    private final Map<String, Double> variables;

    public ComputeExpressionVisitor(Map<String, Double> variables) {
        this.variables = variables;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind opKind = expr.getOperation();
        double left_operand = (double) expr.getLeft().accept(this);
        double right_operand = (double) expr.getRight().accept(this);
        return switch (opKind) {
            case PLUS -> left_operand + right_operand;
            case MINUS -> left_operand - right_operand;
            case MULT -> left_operand * right_operand;
            case DIV -> left_operand / right_operand;
        };
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitVariable(VariableExpression expr) {
       return variables.get(expr.getName());
    }
}
