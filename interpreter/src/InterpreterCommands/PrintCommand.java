package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.InterpreterData;

public class PrintCommand extends AbstractCommand {

	public PrintCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 2)
			return -1;
		if (!args.get(0).contentEquals("print"))
			return -1;

		if (args.get(1).charAt(0) == '"')
			System.out.println(args.get(1).substring(1, args.get(1).length() - 1));
		else
			System.out.println(new ExpressionCreator().translater(args.get(1), this.data.getSymbolTable()).calculate());
		return 2;
	}
}
