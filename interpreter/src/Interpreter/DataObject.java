package Interpreter;

public class DataObject {

	private double val;
	private String otherSideVariable = null;

	public DataObject() {
	}

	public DataObject(String other) {
		this.otherSideVariable = other;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public String getOtherSideVariable() {
		return otherSideVariable;
	}

	public void setOtherSideVariable(String otherVariable) {
		this.otherSideVariable = otherVariable;
	}

}
