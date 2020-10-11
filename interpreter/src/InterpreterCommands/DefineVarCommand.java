package InterpreterCommands;

import java.util.List;

import Interpreter.InterpreterData;

public class DefineVarCommand extends AbstractCommand {

	public DefineVarCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (!args.get(0).contentEquals("var"))
			return -1;

		if (args.get(3).contentEquals("bind")) {
			String boundedTo = args.get(4);
			if (boundedTo.charAt(0) == '\"')
				boundedTo = boundedTo.substring(1, boundedTo.length() - 1);
			this.data.getSymbolTable().createVariable(args.get(1), boundedTo);
			return 5;
		} else {
			this.data.getSymbolTable().createVariable(args.get(1), null);
			if (args.get(2).contentEquals("="))
				return 1; // let the work for the assignment command
			return 2;
		}
	}
}
