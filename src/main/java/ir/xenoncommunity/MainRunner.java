package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;
import ir.xenoncommunity.utils.SocketUtils;
import ir.xenoncommunity.utils.TaskManager;
import lombok.Getter;
import lombok.val;

@Getter
public class MainRunner {
	public final CommandLineParser parser;
	public final TaskManager taskManager;
	public final SocketUtils socketUtils;

	public MainRunner(CommandLineParser parser) {
		this.parser = parser;
		this.taskManager = new TaskManager();
		this.socketUtils = new SocketUtils();
	}
	public void run() {
		val isResult = Main.runner.parser.get("--sendResult", Boolean.class);
		val isKeepAlive = Main.runner.parser.get("--keepAlive", Boolean.class);
	}
}