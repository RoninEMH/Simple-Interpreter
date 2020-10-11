package test;

import Interpreter.InterpreterExecutor;;

public class MyInterpreter {

	public static int interpret(String[] lines) {

		InterpreterExecutor executer = InterpreterExecutor.getInterpreterExecutor();
		try {
			executer.runScript(lines);
			return (int) executer.getReturnValue();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
