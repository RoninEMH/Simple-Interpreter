package Expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import Interpreter.SymbolTable;

public class ExpressionCreator {

	public Expression translater(String exp, SymbolTable symTable) {

		Scanner scanner = new Scanner(exp);
		Pattern p = Pattern.compile("[\\*\\+-/()]"); // skips *,/,-,+,() in scanning
		scanner.useDelimiter(p);

		Queue<String> expressionQueue = new LinkedList<String>();
		Stack<String> operatorsStack = new Stack<String>();

		List<Character> prioritizeOperators = new ArrayList<Character>();
		prioritizeOperators.add('*');
		prioritizeOperators.add('/');

		List<Character> regularOperators = new ArrayList<Character>();
		regularOperators.add('+');
		regularOperators.add('-');

		List<Character> Sograim = new ArrayList<Character>();
		Sograim.add('(');
		Sograim.add(')');

		while (scanner.hasNext()) {

			boolean needToPushNumberFlag = true;
			String str = scanner.next();
			exp = exp.substring(str.length());

			while (!exp.isEmpty() && (prioritizeOperators.contains(exp.charAt(0))
					|| regularOperators.contains(exp.charAt(0)) || Sograim.contains(exp.charAt(0)))) {
				if (exp.charAt(0) == '(')
					operatorsStack.push(new StringBuilder().append(exp.charAt(0)).toString());

				else if (exp.charAt(0) == ')') {
					if (needToPushNumberFlag && !str.isEmpty()) {
						expressionQueue.add(str);
						needToPushNumberFlag = false;
					}
					while (!operatorsStack.empty() && !operatorsStack.lastElement().contentEquals("(")) {
						expressionQueue.add(operatorsStack.pop());
					}
					operatorsStack.pop();
				} else {
					if (!str.isEmpty() && needToPushNumberFlag) {
						expressionQueue.add(str);
						needToPushNumberFlag = false;
					}

					if (regularOperators.contains(exp.charAt(0))) {
						while (!operatorsStack.empty()
								&& prioritizeOperators.contains(operatorsStack.lastElement().charAt(0)))
							expressionQueue.add(operatorsStack.pop());
					}
					operatorsStack.push(new StringBuilder().append(exp.charAt(0)).toString());
				}
				exp = exp.substring(1);
			}
			if (!str.isEmpty() && needToPushNumberFlag)
				expressionQueue.add(str);
		}
		scanner.close();
		while (!operatorsStack.empty())
			expressionQueue.add(operatorsStack.pop());
		Stack<String> expressions = new Stack<String>();
		for (String s : expressionQueue)
			expressions.push(s);

		return this.fromStackOfExpressionsToExpression(expressions, symTable);
	}

	private Expression fromStackOfExpressionsToExpression(Stack<String> expressions, SymbolTable symbolTable) {

		String firstToken = expressions.pop();
		try {
			double number = Double.parseDouble(firstToken);
			return new Number(number);
		} catch (NumberFormatException e) {
		}

		if (firstToken.charAt(0) != '+' && firstToken.charAt(0) != '-' && firstToken.charAt(0) != '/'
				&& firstToken.charAt(0) != '*')
			return new Variable(firstToken, symbolTable);

		Expression rightExpr = this.fromStackOfExpressionsToExpression(expressions, symbolTable);
		Expression leftExpr = this.fromStackOfExpressionsToExpression(expressions, symbolTable);

		switch (firstToken) {
		case "+":
			return new Plus(leftExpr, rightExpr);
		case "-":
			return new Minus(leftExpr, rightExpr);
		case "/":
			return new Div(leftExpr, rightExpr);
		case "*":
			return new Mul(leftExpr, rightExpr);
		}

		return null;
	}
}