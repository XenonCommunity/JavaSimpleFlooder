package ir.xenoncommunity.jss.utils;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Logger {
    @Setter
    private String section = "NONE";
    @Setter
    private LEVEL currentLevel = LEVEL.INFO;

    public void log(final LEVEL level, final String message, final Object... args) {
        if (level.isHigher(currentLevel)) return;

        System.out.printf("[%s] [%s] >> %s", level, section, String.format(message, args));

        if (level != LEVEL.VERBOSE) {
            System.out.println();
        }

    }

    @RequiredArgsConstructor
    public enum LEVEL {
        VERBOSE(0),
        DEBUG(1),
        INFO(2),
        ERROR(3),
        WARN(4);

        private final Integer level;

        boolean isHigher(LEVEL level) {
            return this.level >= level.level;
        }
    }
}
