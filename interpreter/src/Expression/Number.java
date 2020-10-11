package Expression;

public class Number implements Expression {

	private double val;

	public Number(double num) {
		this.val = num;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.val;
	}

}
