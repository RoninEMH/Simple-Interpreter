package InterpreterCommands;

import java.util.List;

import Interpreter.CommandExecutor;
import Interpreter.InterpreterData;

public class IfCommand extends ConditionParser {

	public IfCommand(InterpreterData data, CommandExecutor executer) {
		super(data, executer);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 6)
			return -1;
		if (!args.get(0).contentEquals("if"))
			return -1;
		boolean conditionResult;
		try {
			conditionResult = this.calculateCondition(args.subList(1, args.size()));
		} catch (Exception e) {
			return -1;
		}

		int toContinue = 5;
		if (conditionResult == true) {
			args = args.subList(5, args.size());
			return toContinue + this.runInnerCommands(args);
		} else {
			int elsePosition = this.findEndOfCondition(args);
			args = args.subList(elsePosition, args.size());
			if (!args.get(0).contentEquals("else"))
				return elsePosition;
			args = args.subList(1, args.size());
			return elsePosition + 1 + this.runInnerCommands(args);
		}
	}

}
