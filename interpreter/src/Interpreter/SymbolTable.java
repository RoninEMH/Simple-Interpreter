package Interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {

	private ConcurrentHashMap<String, DataObject> symbolTable;
	private List<String> defaultSymbols;

	public SymbolTable(List<String> defaultSymbols) {

		this.symbolTable = new ConcurrentHashMap<String, DataObject>();
		this.symbolTable.put("simX", new DataObject("simX"));
		this.symbolTable.put("simY", new DataObject("simY"));
		this.symbolTable.put("simZ", new DataObject("simZ"));
		this.defaultSymbols = new LinkedList<String>();
		this.defaultSymbols.add("simX");
		this.defaultSymbols.add("simY");
		this.defaultSymbols.add("simZ");
	}

	public List<String> getDefaultSymbols() {
		return this.defaultSymbols;
	}

	public void createVariable(String name, String boundedTo) {

		if (this.symbolTable.containsKey(name))
			return;
		if (boundedTo == null)
			this.symbolTable.put(name, new DataObject());
		else {
			DataObject boundedToVar = this.symbolTable.get(boundedTo);
			if (boundedToVar == null)
				return;
			this.symbolTable.put(name, boundedToVar);
		}
	}

	public String getBoundedToVariable(String name) {

		return this.symbolTable.get(name).getOtherSideVariable();
	}

	public void setBoundedToVariable(String name, String boundedTo) {

		this.symbolTable.get(name).setOtherSideVariable(boundedTo);
		this.symbolTable.put(name, this.symbolTable.get(boundedTo));
	}

	synchronized public void setVariable(String name, double data) {

		DataObject variableData = this.symbolTable.get(name);
		variableData.setVal(data);
		this.symbolTable.replace(name, variableData);
	}

	synchronized public double getVariable(String name) {
		return this.symbolTable.get(name).getVal();
	}

}
