package Interpreter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SimulatorConnection {

	private Socket simulator;

	public SimulatorConnection(String ip, int port) {
		try {
			this.simulator = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendVariable(String variableName, double value) {

		StringBuilder commandLine = new StringBuilder();
		commandLine.append("set ").append(variableName).append(" ").append(Double.toString(value));
		try {
			PrintWriter commandSender = new PrintWriter(this.simulator.getOutputStream());
			String toSend = commandLine.toString();
			commandSender.println(toSend);
			commandSender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			PrintWriter commandSender = new PrintWriter(this.simulator.getOutputStream());
			commandSender.println("bye");
			commandSender.flush();
			commandSender.close();
			this.simulator.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
