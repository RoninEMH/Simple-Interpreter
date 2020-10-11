package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.Couple;
import Interpreter.InterpreterData;

public class AssignmentCommand extends AbstractCommand {

	public AssignmentCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (!args.get(1).contentEquals("="))
			return -1;
		if (args.get(2).contentEquals("bind")) {
			String boundedTo = args.get(3);
			if (boundedTo.charAt(0) == '\"')
				boundedTo = boundedTo.substring(1, boundedTo.length() - 1);
			this.data.getSymbolTable().setBoundedToVariable(args.get(0), boundedTo);
			return 4;
		}
		Couple<String, Integer> expressionString = this.createExpressionString(args.subList(2, args.size()));
		double expressionValue = new ExpressionCreator()
				.translater(expressionString.getFirst(), this.data.getSymbolTable()).calculate();

		this.data.getSymbolTable().setVariable(args.get(0), expressionValue);
		if (this.data.getSymbolTable().getBoundedToVariable(args.get(0)) != null)
			this.data.getSimulatorConnection()
					.sendVariable(this.data.getSymbolTable().getBoundedToVariable(args.get(0)), expressionValue);

		return expressionString.getSecond() + 2;
	}

}
