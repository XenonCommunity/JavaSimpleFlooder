package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;

public class MainRunner {
	private final CommandLineParser parser;

	public MainRunner(CommandLineParser parser) {
		this.parser = parser;
	}

	public void run() {
		System.out.println(parser.get("-examplestring", String.class));
		System.out.println(parser.get("-exampleinteger", Integer.class));
		System.out.println(parser.get("-exampleboolean", Boolean.class));
	}
}