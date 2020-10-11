package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.InterpreterData;
import Interpreter.SimulatorConnection;

public class ConnectCommand extends AbstractCommand {

	public ConnectCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 3)
			return -1;
		if (!args.get(0).contentEquals("connect"))
			return -1;

		double port = new ExpressionCreator().translater(args.get(2), this.data.getSymbolTable()).calculate();
		this.data.setSimulatorConnection(new SimulatorConnection(args.get(1), (int) port));
		return 3;
	}

}
