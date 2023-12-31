package ir.xenoncommunity;

import lombok.experimental.UtilityClass;

public class Main{
	public static String[] args;
	public static void main(final String[] args){
		Main.args = args;
		MainRunner.run();
	}
}