package Interpreter;

import java.util.LinkedList;
import java.util.List;

public class InterpreterExecutor {

	private static InterpreterExecutor executor = null;
	private double retValue;

	private InterpreterExecutor() {
	}

	public void runScript(String[] script) throws Exception {

		List<String> scriptTokens = Lexer.lexerScript(script);
		InterpreterData interpreterData = new InterpreterData(getAllSymbolsFromScript(scriptTokens));
		CommandExecutor commandExecuter = new CommandExecutor(interpreterData);
		while (scriptTokens.size() > 0) {
			int toContinue = commandExecuter.runCommand(scriptTokens);
			if (toContinue == -1)
				throw new Exception("script is unknown to interpreter");
			scriptTokens = scriptTokens.subList(toContinue, scriptTokens.size());
		}
		this.retValue = interpreterData.getScriptReturnValue();
	}

	public double getReturnValue() {
		return this.retValue;
	}

	public static InterpreterExecutor getInterpreterExecutor() { // creating singleton

		if (InterpreterExecutor.executor == null)
			InterpreterExecutor.executor = new InterpreterExecutor();
		return InterpreterExecutor.executor;
	}

	private List<String> getAllSymbolsFromScript(List<String> tokens) {

		List<String> result = new LinkedList<String>();
		for (int i = 0; i < tokens.size(); i++)
			if (tokens.get(i).contentEquals("bind")) {
				if (tokens.get(i).charAt(0) == '\"')
					result.add(tokens.get(i + 1).substring(1, tokens.get(i + 1).length() - 1));// remove the "" in the
																								// edges of the token
				else
					result.add(tokens.get(i + 1));
			}
		return result;
	}
}
