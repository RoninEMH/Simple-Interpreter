package Expression;

public class Plus extends BinaryExpression {

	public Plus(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.left.calculate() + this.right.calculate();
	}

}
