package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;

public class Main {
	public static void main(String[] args) {
		CommandLineParser parser = new CommandLineParser(args);
		MainRunner runner = new MainRunner(parser);
		runner.run();
	}
}