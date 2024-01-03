package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;

public class Main {
	public static MainRunner runner;
	public static void main(String[] args) {
		Main.runner = new MainRunner(new CommandLineParser(args));
		runner.run();
	}
}