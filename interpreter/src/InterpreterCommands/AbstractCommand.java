package InterpreterCommands;

import java.util.LinkedList;
import java.util.List;

import Interpreter.Couple;
import Interpreter.InterpreterData;

public abstract class AbstractCommand implements Command {

	protected InterpreterData data;
	private List<String> currentCommands;

	public AbstractCommand(InterpreterData data) {

		this.data = data;
		this.currentCommands = new LinkedList<String>();
		this.currentCommands.add("openDataServer");
		this.currentCommands.add("connect");
		this.currentCommands.add("var");
		this.currentCommands.add("=");
		this.currentCommands.add("if");
		this.currentCommands.add("while");
		this.currentCommands.add("print");
		this.currentCommands.add("return");
		this.currentCommands.add("sleep");
		this.currentCommands.add("disconnect");
	}

	public Couple<String, Integer> createExpressionString(List<String> expressionTokens) {

		StringBuilder expressionBuilder = new StringBuilder();
		int i;
		for (i = 0; i < expressionTokens.size(); i++) {
			if (this.currentCommands.contains(expressionTokens.get(i))
					|| ((i + 1) < expressionTokens.size() && expressionTokens.get(i + 1).contentEquals("="))
					|| expressionTokens.get(i).contentEquals("}")) {
				Couple<String, Integer> result = new Couple<String, Integer>();
				result.setFirst(expressionBuilder.toString());
				result.setSecond(i);
				return result;
			}
			expressionBuilder.append(expressionTokens.get(i));
		}
		Couple<String, Integer> result = new Couple<String, Integer>();
		result.setFirst(expressionBuilder.toString());
		result.setSecond(i);
		return result;
	}
}
