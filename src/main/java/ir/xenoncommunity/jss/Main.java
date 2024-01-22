package ir.xenoncommunity.jss;

import ir.xenoncommunity.jss.utils.CommandLineParser;

public class Main {
    public static void main(String[] args) {
        JSSAttack runner = new JSSAttack(new CommandLineParser(args));
        runner.run();
    }
}