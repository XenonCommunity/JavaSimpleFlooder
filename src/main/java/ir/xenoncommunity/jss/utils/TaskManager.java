package ir.xenoncommunity.jss.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    public final ExecutorService tasks;

    public TaskManager(Integer maxThreads) {
        tasks = Executors.newFixedThreadPool(maxThreads, Executors.defaultThreadFactory());
    }

    public void add(final Runnable threadIn) {
        tasks.submit(threadIn);
    }

    public void doTasks(TimeUnit timeUnit, int time) throws InterruptedException {
        if (tasks.awaitTermination(time, timeUnit)) {
            Logger.log(Logger.LEVEL.ERROR, "Failed to shutdown task manager after " + time + " " + timeUnit);
        }

        tasks.shutdown();
    }
}
