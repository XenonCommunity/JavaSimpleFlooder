package ir.xenoncommunity.jss.utils;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class AttackStatics {
    private final AtomicLong ppsCounter = new AtomicLong(0L);
    private final AtomicLong bpsCounter = new AtomicLong(0L);
    private final AtomicLong cpsCounter = new AtomicLong(0L);

    private final Integer ppsLimit;

    public void print() {
        Logger.log(Logger.LEVEL.VERBOSE, "PPS: %d, BPS: %d, CPS: %d", ppsCounter.get(), bpsCounter.get(), cpsCounter.get());
    }

    public boolean isLimitReached() {
        return ppsCounter.get() >= ppsLimit;
    }

    public void cps() {
        cpsCounter.incrementAndGet();
    }

    public void pps() {
        ppsCounter.incrementAndGet();
    }

    public void bps(int bytes) {
        if (bytes <= 0) return;
        bpsCounter.addAndGet(bytes);
    }
}
