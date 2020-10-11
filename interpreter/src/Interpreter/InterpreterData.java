package Interpreter;

import java.util.List;

public class InterpreterData {

	private SymbolTable symbolTable;
	private SimulatorConnection simulatorConnection;
	private double scriptReturnValue = 0;

	public InterpreterData(List<String> defaultSymbols) {
		this.symbolTable = new SymbolTable(defaultSymbols);
		// we don't initialize simulatorConnection because it being initialized in
		// connectCommand
	}

	public SymbolTable getSymbolTable() {
		return this.symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public SimulatorConnection getSimulatorConnection() {
		return this.simulatorConnection;
	}

	public void setSimulatorConnection(SimulatorConnection simulatorConnection) {
		this.simulatorConnection = simulatorConnection;
	}

	public double getScriptReturnValue() {
		return this.scriptReturnValue;
	}

	public void setScriptReturnValue(double scriptReturnValue) {
		this.scriptReturnValue = scriptReturnValue;
	}

}
