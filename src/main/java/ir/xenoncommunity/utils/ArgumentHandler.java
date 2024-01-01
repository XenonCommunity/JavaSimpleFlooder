package ir.xenoncommunity.utils;

import ir.xenoncommunity.Main;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ArgumentHandler {
	public <T> T getArg(final String argIn) {
		for(int index = 0 ; index < Main.args.length; index++){
			if(Main.args[index].equals(argIn)) {
				var temp = (index + 1 < Main.args.length) ? (Main.args[index + 1]) : ("");
				if(temp.startsWith("-") ) {
					return (T) Boolean.valueOf(true);
				}else {
					switch(temp){
						case "":
							return (T) Boolean.valueOf(true);
						case "true":
						case "false":
							return (T) Boolean.valueOf(Boolean.parseBoolean(temp));
						case default:
							try {
								return (T) Integer.valueOf(Integer.parseInt(temp));
							} catch (Exception e) {
								return (T) temp;
							}
					}
				}
			}
		}
		return (T) "NO ARGS";
	}
}