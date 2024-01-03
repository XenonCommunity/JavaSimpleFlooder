package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;
import ir.xenoncommunity.utils.Logger;
import ir.xenoncommunity.utils.SocketUtils;
import ir.xenoncommunity.utils.TaskManager;
import lombok.Getter;
import lombok.val;

@Getter
public class MainRunner {
	public final CommandLineParser parser;
	public final TaskManager taskManager;
	public final SocketUtils socketUtils;
	public final Logger logger = new Logger(true, true);

	public MainRunner(CommandLineParser parser) {
		this.parser = parser;
		this.taskManager = new TaskManager();
		this.socketUtils = new SocketUtils();
	}

	public void run() {
		getLogger().print(Logger.LEVEL.INFO,"", "test");
		val ip = Main.runner.parser.get("--ip", String.class);
		val port = Main.runner.parser.get("--port", Integer.class);
		val maxThreads = Main.runner.parser.get("--threads", Integer.class);
		val isResult = Main.runner.parser.get("--sendResult", Boolean.class);
		val isKeepAlive = Main.runner.parser.get("--keepAlive", Boolean.class);
		for(int i = 0; i <= maxThreads; i++){
			getTaskManager().add(new Thread(() -> {
				while (true) {
					socketUtils.connect(ip, port, isResult, isKeepAlive);
				}
			}));
		}
		getTaskManager().doTasks();
	}
}