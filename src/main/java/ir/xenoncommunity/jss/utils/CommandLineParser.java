package ir.xenoncommunity.jss.utils;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class CommandLineParser {

    private final String[] args;
    public <T> T get(final String arg, final Class<T> type, final @Nullable T defaultValue) {
        for (int i = 0; i < args.length - 1; i++) {
            if (!args[i].equals(arg)) continue;

            return args[i + 1].startsWith("-") ? type.cast(Boolean.TRUE) : convertToType(args[i + 1], type);
        }
        return defaultValue;
    }



    private <T> T convertToType(final String value, final Class<T> type) {
        return type.equals(String.class) ?
                type.cast(value)
                :
                (type.equals(Integer.class)
                ?
                type.cast(Integer.parseInt(value))
                :
                (type.equals(Boolean.class)
                ? type.cast(Boolean.parseBoolean(value))
                : null));
    }
}