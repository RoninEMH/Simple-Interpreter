package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.CommandExecutor;
import Interpreter.InterpreterData;

public abstract class ConditionParser extends AbstractCommand {

	private CommandExecutor executor;

	protected ConditionParser(InterpreterData data, CommandExecutor executer) {
		super(data);
		this.executor = executer;
	}

	protected boolean calculateCondition(List<String> args) throws Exception {

		if (args.size() < 3)
			throw new Exception("command need to have 3 parameters");

		double leftSideValue = new ExpressionCreator().translater(args.get(0), this.data.getSymbolTable()).calculate();
		double rightSideValue = new ExpressionCreator().translater(args.get(2), this.data.getSymbolTable()).calculate();

		switch (args.get(1)) {
		case "==":
			return leftSideValue == rightSideValue;
		case "!=":
			return leftSideValue != rightSideValue;
		case "<":
			return leftSideValue < rightSideValue;
		case ">":
			return leftSideValue > rightSideValue;
		case "<=":
			return leftSideValue <= rightSideValue;
		case ">=":
			return leftSideValue >= rightSideValue;
		default:
			throw new Exception("unknown condition to interpreter");
		}
	}

	protected int findEndOfCondition(List<String> conditionTokens) {

		conditionTokens = conditionTokens.subList(5, conditionTokens.size());
		int numOfBracketsLeft = 0;
		int toContinue = 5;
		while (numOfBracketsLeft >= 0) {
			if (conditionTokens.get(0).charAt(0) == '{')
				numOfBracketsLeft++;
			if (conditionTokens.get(0).charAt(0) == '}')
				numOfBracketsLeft--;
			conditionTokens = conditionTokens.subList(1, conditionTokens.size());
			toContinue++;
		}
		return toContinue;
	}

	protected int runInnerCommands(List<String> conditionTokens) {

		int toContinue = 0;
		while (true) {
			if (conditionTokens.get(0).contentEquals("}"))
				return toContinue + 1;
			int currentCommandtoContinue = this.executor.runCommand(conditionTokens);
			if (currentCommandtoContinue == -1)
				return -1;
			conditionTokens = conditionTokens.subList(currentCommandtoContinue, conditionTokens.size());
			toContinue += currentCommandtoContinue;
		}
	}
}
