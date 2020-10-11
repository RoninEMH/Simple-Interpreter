package Expression;

public class Mul extends BinaryExpression {

	public Mul(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.left.calculate() * this.right.calculate();
	}

}
