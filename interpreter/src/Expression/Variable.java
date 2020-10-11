package Expression;

import Interpreter.SymbolTable;

public class Variable implements Expression {

	private String name;
	SymbolTable symTable;

	public Variable(String name, SymbolTable symTable) {
		this.name = name;
		this.symTable = symTable;
	}

	@Override
	public double calculate() {
		// TODO Auto-generated method stub
		return this.symTable.getVariable(this.name);
	}

}
