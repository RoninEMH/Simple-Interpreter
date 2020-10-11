package InterpreterCommands;

import java.util.List;

import Interpreter.InterpreterData;

public class DisconnectCommand extends AbstractCommand {

	public DisconnectCommand(InterpreterData data) {
		super(data);
	}

	@Override
	public int doCommand(List<String> args) {

		if (args.size() < 1)
			return -1;
		if (!args.get(0).contentEquals("disconnect"))
			return -1;
		this.data.getSimulatorConnection().close();
		return 1;
	}
}
