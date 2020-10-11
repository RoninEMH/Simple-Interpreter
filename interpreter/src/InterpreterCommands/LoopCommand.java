package InterpreterCommands;

import java.util.List;

import Interpreter.CommandExecutor;
import Interpreter.InterpreterData;

public class LoopCommand extends ConditionParser {

	public LoopCommand(InterpreterData data, CommandExecutor executer) {
		super(data, executer);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 6)
			return -1;
		if (!args.get(0).contentEquals("while"))
			return -1;
		args = args.subList(1, args.size());
		int toContinue = 5;
		try {
			if (this.calculateCondition(args) == true) {
				do {
					toContinue = 5 + this.runInnerCommands(args.subList(4, args.size()));
				} while ((this.calculateCondition(args) == true));
				return toContinue;
			} else
				return toContinue + 1;
		} catch (Exception e) {
			return -1;
		}
	}
}
