package Expression;

public class Div extends BinaryExpression {

	public Div(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.left.calculate() / this.right.calculate();
	}

}
