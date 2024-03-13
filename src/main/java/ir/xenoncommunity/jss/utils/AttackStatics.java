package ir.xenoncommunity.jss.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;

import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class AttackStatics {
    private final AtomicLong ppsCounter = new AtomicLong(0L);
    private final AtomicLong bpsCounter = new AtomicLong(0L);
    private final AtomicLong cpsCounter = new AtomicLong(0L);

    @Setter
    @Getter
    private boolean running = true;
    private final Integer ppsLimit;

    /**
     * Reset counters and log the number of packets, bytes, and commands per second.
     */
    public void second() {
        val packetsPerSecond = ppsCounter.getAndSet(0);
        val bytesPerSecond = bpsCounter.getAndSet(0);
        val commandsPerSecond = cpsCounter.getAndSet(0);
        // Format the output string
        String formattedOutput = String.format("PPS: %,d, BPS: %s\\ps, CPS: %,d%n", packetsPerSecond, formatBytes(bytesPerSecond), commandsPerSecond);
        // Log the formatted output
        Logger.log(Logger.LEVEL.VERBOSE, formattedOutput);
    }


    /**
     * Formats the given number of bytes into a string representation.
     * @param bytes The number of bytes to be formatted
     * @return The formatted string representation of the bytes
     */
    private String formatBytes(final long bytes) {
        // Convert bytes to bits
        val bits = (double) bytes * 8;

        // Check if bytes is greater than or equal to 1Gb
        if (bytes >= 1024 * 1024 * 1024) {
            // Return formatted string for Gb
            return String.format("%.2fGb", bits / (1024.0 * 1024.0 * 1024.0));
        } else if (bytes >= 1024 * 1024) { // Check if bytes is greater than or equal to 1Mb
            // Return formatted string for Mb
            return String.format("%.2fMb", bits / (1024.0 * 1024.0));
        } else { // If bytes is less than 1Mb
            // Return formatted string for Kb
            return String.format("%.2fKb", bits / 1024.0);
        }
    }

    /**
     * Checks if the limit of requests per second (pps) has been reached.
     *
     * @return true if the pps limit has been reached, false otherwise
     */
    public boolean isLimitReached() {
        return ppsCounter.get() >= ppsLimit && ppsLimit != -1;
    }

    public void cps() {
        cpsCounter.incrementAndGet();
    }

    public void pps() {
        ppsCounter.incrementAndGet();
    }

    public void bps(final int bytes) {
        if (bytes <= 0) return;
        bpsCounter.addAndGet(bytes);
    }
}
