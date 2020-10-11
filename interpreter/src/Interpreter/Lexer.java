package Interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Lexer {

	static public List<String> lexerScript(String[] script) {

		List<String> tokens = new LinkedList<String>();
		for (String scriptLine : script) {
			Scanner scanner = new Scanner(scriptLine);
			while (scanner.hasNext()) {
				String token = scanner.next();
				if (token.contentEquals("="))
					tokens.add(token);
				else {
					String[] splitTokens = token.split("=");
					for (String splitToken : splitTokens) {
						tokens.add(splitToken);
						tokens.add("=");
					}
					tokens.remove(tokens.size() - 1);
				}
			}
			scanner.close();
		}
		return tokens;
	}
}
