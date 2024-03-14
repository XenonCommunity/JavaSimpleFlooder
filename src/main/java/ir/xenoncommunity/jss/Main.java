package ir.xenoncommunity.jss;

import ir.xenoncommunity.jss.utils.CommandLineParser;

public class Main {
    /**
     * Entry point of the program
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        // Create a JSSAttack object with the command line arguments
        JSSAttack runner = new JSSAttack(new CommandLineParser(args));
        // Run the JSSAttack program
        runner.run();
    }
}