package ir.xenoncommunity;

import lombok.experimental.UtilityClass;
import ir.xenoncommunity.utils.ArgumentHandler;

@UtilityClass
public class MainRunner{
	public void run(){
		System.out.println((String) ArgumentHandler.getArg("-kir"));
		System.out.println((int) ArgumentHandler.getArg("-kos"));
		System.out.println((boolean) ArgumentHandler.getArg("-kiramtot"));
		System.out.println((boolean) ArgumentHandler.getArg("-kosam"));
		System.out.println((boolean) ArgumentHandler.getArg("-kooooos"));
		System.out.println((boolean) ArgumentHandler.getArg("-rot"));

	}
}