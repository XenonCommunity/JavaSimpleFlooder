package ir.xenoncommunity.utils;

import ir.xenoncommunity.Main;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ArgumentHandler{
	public String getArgS(final String argIn){
		for(int index = 0 ; index < Main.args.length; index++){
			if(Main.args[index].equals(argIn))
				return Main.args[index+1];
		}
		return "NO ARGS";
	}
	public int getArgI(final String argIn){
		for(int index = 0 ; index < Main.args.length; index++){
			if(Main.args[index].equals(argIn))
				return Integer.parseInt(Main.args[index+1]);
		}
		return 0;
	}
	public boolean getArgB(final String argIn){
		for(int index = 0 ; index < Main.args.length; index++){
			if(Main.args[index].equals(argIn)){
				try{
					return Boolean.parseBoolean(Main.args[index+1]);
				}catch(Exception e){
					return true;
				}
			}
		}
		return false;
	}
}