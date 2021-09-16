import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    Map<String, Double> variables = new HashMap<>();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind opKind = expr.getOperation();
        double left_operand = (double) expr.getLeft().accept(this);
        double right_operand = (double) expr.getRight().accept(this);
        return switch (opKind) {
            case plus -> left_operand + right_operand;
            case minus -> left_operand - right_operand;
            case mult -> left_operand * right_operand;
            case div -> left_operand / right_operand;
        };
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitVariable(VariableLiteral expr) {
        if(variables.containsKey(expr.getName())) {
            return variables.get(expr.getName());
        } else {
            System.out.print("value for '" + expr.getName() + "': ");
            Scanner in = new Scanner(System.in);
            String input = in.next();
            double result = Double.parseDouble(input);
            variables.put(expr.getName(), result);
            return result;
        }
    }
}
