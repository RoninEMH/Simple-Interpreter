package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.InterpreterData;

public class SleepCommand extends AbstractCommand {

	public SleepCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 2)
			return -1;
		if (!args.get(0).contentEquals("sleep"))
			return -1;
		double howMuchToSleep = new ExpressionCreator().translater(args.get(1), this.data.getSymbolTable()).calculate();
		try {
			Thread.sleep((long) howMuchToSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 2;
	}
}
