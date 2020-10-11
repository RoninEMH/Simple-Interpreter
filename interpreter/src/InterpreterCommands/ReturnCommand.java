package InterpreterCommands;

import java.util.List;

import Expression.ExpressionCreator;
import Interpreter.CommandExecutor;
import Interpreter.Couple;
import Interpreter.InterpreterData;

public class ReturnCommand extends AbstractCommand {

	private CommandExecutor commandExecuter;

	public ReturnCommand(InterpreterData data, CommandExecutor commandex) {
		super(data);
		this.commandExecuter = commandex;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int doCommand(List<String> args) {

		if (!args.get(0).contentEquals("return"))
			return -1;
		Couple<String, Integer> expression = this.createExpressionString(args.subList(1, args.size()));
		this.commandExecuter.stop();
		this.data.setScriptReturnValue(
				new ExpressionCreator().translater(expression.getFirst(), this.data.getSymbolTable()).calculate());

		return expression.getSecond() + 1;
	}
}
