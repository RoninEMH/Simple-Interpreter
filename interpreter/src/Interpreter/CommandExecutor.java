package Interpreter;

import java.util.HashMap;
import java.util.List;

import InterpreterCommands.AssignmentCommand;
import InterpreterCommands.Command;
import InterpreterCommands.ConnectCommand;
import InterpreterCommands.DefineVarCommand;
import InterpreterCommands.DisconnectCommand;
import InterpreterCommands.IfCommand;
import InterpreterCommands.LoopCommand;
import InterpreterCommands.OpenDataServerCommand;
import InterpreterCommands.PrintCommand;
import InterpreterCommands.ReturnCommand;
import InterpreterCommands.SleepCommand;

public class CommandExecutor {

	private HashMap<String, Command> commands;

	public CommandExecutor(InterpreterData data) {
		this.commands = new HashMap<String, Command>();
		this.commands.put("openDataServer", new OpenDataServerCommand(data, data.getSymbolTable().getDefaultSymbols()));
		this.commands.put("connect", new ConnectCommand(data));
		this.commands.put("var", new DefineVarCommand(data));
		this.commands.put("=", new AssignmentCommand(data));
		this.commands.put("if", new IfCommand(data, this));
		this.commands.put("while", new LoopCommand(data, this));
		this.commands.put("print", new PrintCommand(data));
		this.commands.put("sleep", new SleepCommand(data));
		this.commands.put("return", new ReturnCommand(data, this));
		this.commands.put("disconnect", new DisconnectCommand(data));
	}

	public int runCommand(List<String> scriptTokens) {

		if (scriptTokens.get(1).contentEquals("="))
			return this.commands.get("=").doCommand(scriptTokens);
		return this.commands.get(scriptTokens.get(0)).doCommand(scriptTokens);
	}

	public void stop() {

		((OpenDataServerCommand) this.commands.get("openDataServer")).stop();
	}
}
