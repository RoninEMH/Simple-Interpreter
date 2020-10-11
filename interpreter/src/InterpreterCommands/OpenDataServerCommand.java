package InterpreterCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Scanner;

import Expression.ExpressionCreator;
import Interpreter.InterpreterData;

public class OpenDataServerCommand extends AbstractCommand {

	private volatile boolean continueRunning = true;
	private Object threadDyeObject = new Object();
	private List<String> defaultSymbols;
	private boolean isThreadCreated = false;
	private boolean hasConnection = false;

	public OpenDataServerCommand(InterpreterData data, List<String> defaultSymbols) {
		super(data);
		this.defaultSymbols = defaultSymbols;
	}

	@Override
	public int doCommand(List<String> args) {
		// TODO Auto-generated method stub
		if (!args.get(0).contentEquals("openDataServer"))
			return -1;
		if (args.size() < 3)
			return -1;

		double port = new ExpressionCreator().translater(args.get(1), this.data.getSymbolTable()).calculate();
		double Hz = new ExpressionCreator().translater(args.get(2), this.data.getSymbolTable()).calculate();

		Object locker = new Object();
		this.isThreadCreated = true;

		new Thread(() -> {
			try {
				ServerSocket theServer = new ServerSocket((int) port);
				theServer.setSoTimeout(5000);
				try {
					Socket aClient = theServer.accept();
					// System.out.println("connected...");
					synchronized (locker) {
						this.hasConnection = true;
						locker.notify();
					}
					BufferedReader simulatorInput = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
					while (this.continueRunning) {

						String line;
						while ((line = simulatorInput.readLine()) != null && this.continueRunning) {

							Scanner scanner1 = new Scanner(line);
							scanner1.useDelimiter(",");
							int i = 0;
							while (scanner1.hasNext()) {
								this.data.getSymbolTable().setVariable(this.defaultSymbols.get(i),
										Double.parseDouble(scanner1.next()));
								i++;
							}
							scanner1.close();
							try {
								Thread.sleep((long) (1000 / Hz));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						Scanner scanner2 = new Scanner(line);
						scanner2.useDelimiter(",");
						int j = 0;
						while (scanner2.hasNext()) {
							this.data.getSymbolTable().setVariable(this.defaultSymbols.get(j),
									Double.parseDouble(scanner2.next()));
							j++;
						}
						scanner2.close();
					}
					simulatorInput.close();
					aClient.close();
				} catch (SocketTimeoutException ete) {
					ete.printStackTrace();
				}
				theServer.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			synchronized (this.threadDyeObject) {
				this.threadDyeObject.notify();
			}
		}).start();

		try {
			if (!this.hasConnection) {
				synchronized (locker) {
					if (!this.hasConnection)
						locker.wait();
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IllegalMonitorStateException e) {
			e.printStackTrace();
		}
		return 3;
	}

	public void stop() {

		if (this.isThreadCreated) {
			synchronized (this.threadDyeObject) {
				this.continueRunning = false;
				try {
					this.threadDyeObject.wait();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			this.isThreadCreated = false;
		}
	}
}
