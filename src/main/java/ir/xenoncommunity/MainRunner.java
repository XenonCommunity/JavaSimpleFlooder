package ir.xenoncommunity;

import ir.xenoncommunity.utils.CommandLineParser;
import ir.xenoncommunity.utils.Logger;
import ir.xenoncommunity.utils.SocketUtils;
import ir.xenoncommunity.utils.TaskManager;
import lombok.Getter;
import lombok.val;

@Getter
public class MainRunner {
	public boolean isDebug;
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
		this.isDebug  = Main.runner.parser.get("--debug", Boolean.class);
		val ip = Main.runner.parser.get("--ip", String.class);
		val port = Main.runner.parser.get("--port", Integer.class);
		val maxThreads = Main.runner.parser.get("--threads", Integer.class);
		val isResult = Main.runner.parser.get("--sendResult", Boolean.class);
		val isKeepAlive = Main.runner.parser.get("--keepAlive", Boolean.class);
		if(this.isDebug()){
			getLogger().setSection("DEBUG");
			getLogger().print(Logger.LEVEL.INFO, "ip is: " + ip);
			getLogger().print(Logger.LEVEL.INFO, "port is: " + port);
			getLogger().print(Logger.LEVEL.INFO, "maxThreads is: " + maxThreads);
			getLogger().print(Logger.LEVEL.INFO, "isResult is: " + isResult);
			getLogger().print(Logger.LEVEL.INFO, "isKeepAlive is: " + isKeepAlive);
		}
		for(int i = 0; i <= maxThreads; i++){
			if(this.isDebug)
				getLogger().print(Logger.LEVEL.INFO, "adding new thread. max: " + maxThreads);
			getTaskManager().add(new Thread(() -> {
				while (true)
					socketUtils.connect(ip, port, isResult, isKeepAlive);
			}));
		}
		getLogger().print(Logger.LEVEL.INFO,  "doing tasks...");
		getTaskManager().doTasks();
	}
}